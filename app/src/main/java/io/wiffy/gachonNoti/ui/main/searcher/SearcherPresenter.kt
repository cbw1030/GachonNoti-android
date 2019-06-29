package io.wiffy.gachonNoti.ui.main.searcher

import android.util.Log
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainContract
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Document
import kotlin.collections.ArrayList


class SearcherPresenter(private val mView: MainContract.FragmentSearcher) : MainContract.PresenterSearcher {

    private var loadcnt = 0
    private var errorCnt = 0
    private lateinit var arrayList : ArrayList<String>


    override fun initPresent() {
        mView.initUI()
    }

    override fun getData(yearSemester: String) {
        loadcnt = 0
        errorCnt = 0
        mView.showLoad()
        SearchAsyncTask("searchIsuCD=002", "1", yearSemester, this).execute()
        SearchAsyncTask("searchIsuCD=001", "2", yearSemester, this).execute()
        SearchAsyncTask("searchIsuCD=004", "3", yearSemester, this).execute()
    }

    override fun showLoad() {
        mView.showLoad()
    }

    override fun dismissLoad() {
        loadcnt += 1
        if (loadcnt >= 3) {
            mView.initUI()
            mView.dismissLoad()
        }
    }

    override fun error() {
        errorCnt += 1
        if (errorCnt == 1) {
            mView.dismissLoad()
            mView.errorDialog()
        }
    }

    override fun isDownloaded(year: String, semester: String) {
        val isLoad1 = Util.sharedPreferences.getString(
            "$year-$semester-1", "<noData>"
        )?:"<noData>"
        val isLoad2 = Util.sharedPreferences.getString(
            "$year-$semester-2", "<noData>"
        )?:"<noData>"
        val isLoad3 = Util.sharedPreferences.getString(
            "$year-$semester-3", "<noData>"
        )?:"<noData>"
        if (!isLoad1.contains("<noData>") &&
            !isLoad2.contains("<noData>") &&
            !isLoad3.contains("<noData>")
        ) {
            mView.showBtn(false)
            arrayList = ArrayList()
            makeXML(isLoad1)
            makeXML(isLoad2)
            makeXML(isLoad3)
        } else {
            mView.showBtn(true)
        }
    }

    private fun makeXML(data:String) {
        try {
            val replacedData = data.replace("<?xml version='1.0' encoding='EUC-KR'?>","")

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val sr = StringReader(replacedData)
            val doc: Document = builder.parse(InputSource(sr))
            val nodeList = doc.getElementsByTagName("grid")
            Log.d("asdf", doc.xmlEncoding)
            for (i in 0 until nodeList.length) {
                try{
                    val node = nodeList.item(i)
                    val firstElement = node as Element
                    val room = firstElement.getElementsByTagName("room")
                    val roomNM = room.item(0).childNodes.item(0).nodeValue
                    addArray(roomNM)
//                    if(roomNM.contains(",")){
//                        addArray(roomNM.split(",")[0])
//                        addArray(roomNM.split(",")[1])
//                    }else{
//                        addArray(roomNM)
//                    }
//                    Log.d("asdf", "${i.toString()}$roomNM")
                }catch (e:java.lang.Exception){
                }
            }
        } catch (ex: Exception) { Log.d("asdf", "nononon")}
        arrayList.sort()
        mView.setSpinner(arrayList)
    }

    private fun addArray(roomNM:String){
        if (!arrayList.contains(roomNM)){
            arrayList.add(roomNM)
        }
    }
}