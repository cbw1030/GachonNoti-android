package io.wiffy.gachonNoti.ui.main.searcher

import android.util.Log
import android.view.View
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainContract
import kotlinx.android.synthetic.main.fragment_searcher.view.*
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import java.lang.reflect.Array.getLength
import javax.xml.parsers.DocumentBuilderFactory
import android.R.xml
import org.w3c.dom.Document
import java.util.*
import kotlin.collections.ArrayList


class SearcherPresenter(private val mView: MainContract.FragmentSearcher) : MainContract.PresenterSearcher {

    private var loadcnt = 0
    private var errorCnt = 0
    lateinit private var arrayList : ArrayList<String>;


    override fun initPresent() {
        mView.initUI()
    }

    override fun getdata(yearsemester: String) {
        loadcnt = 0
        errorCnt = 0
        mView.showLoad()
        SearchAsyncTask("searchIsuCD=002", "1", yearsemester, this).execute()
        SearchAsyncTask("searchIsuCD=001", "2", yearsemester, this).execute()
        SearchAsyncTask("searchIsuCD=004", "3", yearsemester, this).execute()
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

    override fun isdownloaded(year: String, semester: String) {
        var isLoad1 = Util.sharedPreferences.getString(
            "$year-$semester-1", "<nodata>"
        )
        var isLoad2 = Util.sharedPreferences.getString(
            "$year-$semester-2", "<nodata>"
        )
        var isLoad3 = Util.sharedPreferences.getString(
            "$year-$semester-3", "<nodata>"
        )
        if (!isLoad1.contains("<nodata>") &&
            !isLoad2.contains("<nodata>") &&
            !isLoad3.contains("<nodata>")
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
            var datar = data.replace("<?xml version='1.0' encoding='EUC-KR'?>","")
            var doc: Document? = null
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val sr = StringReader(datar)
            doc = builder.parse(InputSource(sr))
            val nodeList = doc.getElementsByTagName("grid")
            Log.d("asdf", nodeList.getLength().toString())
            for (i in 0 until nodeList.getLength()) {
                try{
                    val node = nodeList.item(i)
                    val fstElmnt = node as Element
                    val room = fstElmnt.getElementsByTagName("room")
                    var roomNM = room.item(0).getChildNodes().item(0).getNodeValue()
                    addarray(roomNM)
//                    if(roomNM.contains(",")){
//                        addarray(roomNM.split(",")[0])
//                        addarray(roomNM.split(",")[1])
//                    }else{
//                        addarray(roomNM)
//                    }
                    Log.d("asdf", "${i.toString()}$roomNM")
                }catch (e:java.lang.Exception){
                }
            }
        } catch (ex: Exception) { Log.d("asdf", "nononon")}
        Collections.sort(arrayList);
        mView.setSpinner(arrayList)
    }

    private fun addarray(roomNM:String){
        if (!arrayList.contains(roomNM)){
            arrayList.add(roomNM)
        }
    }
}