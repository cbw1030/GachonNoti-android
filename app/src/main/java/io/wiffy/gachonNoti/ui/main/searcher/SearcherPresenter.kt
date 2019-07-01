package io.wiffy.gachonNoti.ui.main.searcher

import android.util.Log
import com.github.eunsiljo.timetablelib.data.TimeData
import com.github.eunsiljo.timetablelib.data.TimeTableData
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.model.Util.Companion.classToTime
import io.wiffy.gachonNoti.ui.main.MainContract
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Document
import kotlin.collections.ArrayList
import java.util.*
import kotlin.Comparator


class SearcherPresenter(private val mView: MainContract.FragmentSearcher) : MainContract.PresenterSearcher {

    private var loadcnt = 0
    private var errorCnt = 0
    lateinit var findBuilding: ArrayList<String>
    lateinit var findRoom: ArrayList<String>
    lateinit var tablearr: ArrayList<ArrayList<TimeData<Any?>>>
    lateinit var load1: String
    lateinit var load2: String
    lateinit var load3: String
    lateinit var mView2: MainContract.PresenterSearchDialog

    override fun initPresent() {
        mView.initUI()
    }

    override fun initPresentDialog(tmp: MainContract.PresenterSearchDialog) {
        mView2 = tmp
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
            mView2.showBtn(false)
            mView2.requestLoad()
            mView.dismissLoad()
        }
    }

    override fun error() {
        errorCnt += 1
        if (errorCnt == 1) {
            mView.dismissLoad()
            mView2.errorDialog()
        }
    }

    override fun isDownloaded(year: String, semester: String) {
        load1 = Util.sharedPreferences.getString(
            "$year-$semester-1", "<nodata>"
        ) ?: "<nodata>"
        load2 = Util.sharedPreferences.getString(
            "$year-$semester-2", "<nodata>"
        ) ?: "<nodata>"
        load3 = Util.sharedPreferences.getString(
            "$year-$semester-3", "<nodata>"
        ) ?: "<nodata>"
        if (!load1.contains("<nodata>") &&
            !load2.contains("<nodata>") &&
            !load3.contains("<nodata>")
        ) {
            mView2.showBtn(false)
            findBuilding = ArrayList()
            findBuildingXML(load1)
            findBuildingXML(load2)
            findBuildingXML(load3)
            findBuilding.sort()
            mView2.setSpinner(findBuilding)
        } else {
            mView2.showBtn(true)
        }
    }

    private fun findBuildingXML(data: String) {
        try {
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val sr = StringReader(data.replace("<?xml version='1.0' encoding='EUC-KR'?>", ""))
            val doc: Document = builder.parse(InputSource(sr))
            val nodeList = doc.getElementsByTagName("grid")

            for (i in 0 until nodeList.length) {
                try {
                    val node = nodeList.item(i)
                    val firstElement = node as Element
                    val room = firstElement.getElementsByTagName("room")
                    val classInformation = ClassDataInformation(
                        firstElement.getElementsByTagName("subjectNm").item(0).childNodes.item(0).nodeValue,
                        firstElement.getElementsByTagName("time").item(0).childNodes.item(0).nodeValue,
                        room.item(0).childNodes.item(0).nodeValue
                    )

                    val roomNM = classInformation.room.split(",")
                    for (x in roomNM) {
                        var tmp = x
                        if (tmp.contains("-")) {
                            tmp = tmp.split("-")[0]
                        }
                        if (!findBuilding.contains(tmp)) {
                            findBuilding.add(tmp)
                        }
                    }

                } catch (e: java.lang.Exception) {

                }
            }
        } catch (ex: Exception) {
            Log.d("asdf", "nononon")
        }

    }

    override fun loadRoom(roomNM: String) {
        findRoom = ArrayList()
        findRoomXML(load1, roomNM)
        findRoomXML(load2, roomNM)
        findRoomXML(load3, roomNM)
        findRoom.sort()
        mView2.setListDialog(findRoom)
    }

    private fun findRoomXML(data: String, roomNMD: String) {

        try {
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val sr = StringReader(data.replace("<?xml version='1.0' encoding='EUC-KR'?>", ""))
            val doc: Document = builder.parse(InputSource(sr))
            val nodeList = doc.getElementsByTagName("grid")
            for (i in 0 until nodeList.length) {
                try {
                    val node = nodeList.item(i)
                    val firstElement = node as Element
                    val room = firstElement.getElementsByTagName("room")
                    val classInformation = ClassDataInformation(
                        firstElement.getElementsByTagName("subjectNm").item(0).childNodes.item(0).nodeValue,
                        firstElement.getElementsByTagName("time").item(0).childNodes.item(0).nodeValue,
                        room.item(0).childNodes.item(0).nodeValue
                    )
                    val roomNM = classInformation.room.split(",")
                    for (x in roomNM) {
                        if (!findRoom.contains(x) && x.contains(roomNMD)) {
                            findRoom.add(x)
                        }
                    }

                } catch (e: java.lang.Exception) {

                }
            }
        } catch (ex: Exception) {
            Log.d("asdf", "nononon")
        }
    }


    class TimeCompare : Comparator<TimeData<Any?>> {
        override fun compare(o1: TimeData<Any?>?, o2: TimeData<Any?>?): Int {
            return o1!!.startMills.compareTo(o2!!.startMills);
        }
    }

    override fun loadTable(str: String) {
        mView.showLoad()
        tablearr = ArrayList(6)
        tablearr.add(ArrayList())
        tablearr.add(ArrayList())
        tablearr.add(ArrayList())
        tablearr.add(ArrayList())
        tablearr.add(ArrayList())
        tablearr.add(ArrayList())
        findTable(load1, str)
        findTable(load2, str)
        findTable(load3, str)

        Collections.sort(tablearr[0], TimeCompare())
        Collections.sort(tablearr[1], TimeCompare())
        Collections.sort(tablearr[2], TimeCompare())
        Collections.sort(tablearr[3], TimeCompare())
        Collections.sort(tablearr[4], TimeCompare())

        val array2 = ArrayList<TimeTableData>()
        array2.add(TimeTableData("월", tablearr[0]))
        array2.add(TimeTableData("화", tablearr[1]))
        array2.add(TimeTableData("수", tablearr[2]))
        array2.add(TimeTableData("목", tablearr[3]))
        array2.add(TimeTableData("금", tablearr[4]))

        mView.setTimeTable(array2,str)
        mView.dismissLoad()
    }


    private fun insertTime(str1: ClassDataInformation, cnt: Int, start: Long, end: Long) {
        tablearr[cnt].add(
            TimeData(
                2,
                str1.name,
                Util.getRandomColorId(),
                R.color.white,
                start,
                end
            )
        )
    }

    private fun numToday(num: Int): String {
        return when (num) {
            0 -> { "월" }
            1 -> { "화" }
            2 -> { "수" }
            3 -> { "목" }
            4 -> { "금" }
            else -> { "토" }
        }
    }

    private fun findTable(data: String, roomNMD: String) {
        try {
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val sr = StringReader(data.replace("<?xml version='1.0' encoding='EUC-KR'?>", ""))
            val doc: Document = builder.parse(InputSource(sr))
            val nodeList = doc.getElementsByTagName("grid")
            for (a in 0 until nodeList.length) {
                try {
                    val node = nodeList.item(a)
                    val firstElement = node as Element
                    val classInformation = ClassDataInformation(
                        firstElement.getElementsByTagName("subjectNm").item(0).childNodes.item(0).nodeValue,
                        firstElement.getElementsByTagName("time").item(0).childNodes.item(0).nodeValue,
                        firstElement.getElementsByTagName("room").item(0).childNodes.item(0).nodeValue
                    )

                    var cntroom = true
                    val tmpR = classInformation.room.replace(" ", "")
                    var tempRoom = tmpR
                    var tempRoom2 = tmpR
                    if (tmpR.contains(",")) {
                        tempRoom = tmpR.split(",")[0]
                        tempRoom2 = tmpR.split(",")[1]
                    }

                    val time = classInformation.time.split(",")
                    val data = Array(5) { "" }
                    for (t in time) {
                        if (t.contains("월")) { data[0] = "${data[0]},$t" }
                        if (t.contains("화")) { data[1] = "${data[1]},$t" }
                        if (t.contains("수")) { data[2] = "${data[2]},$t" }
                        if (t.contains("목")) { data[3] = "${data[3]},$t" }
                        if (t.contains("금")) { data[4] = "${data[4]},$t" }
                    }
                    for (i in 0..4) {
                        if (data[i].contains(",")) {
                            val scom = data[i].replace(" ", "").split(",")
                            if ((cntroom && tempRoom.contains(roomNMD)) ||
                                (!cntroom && tempRoom2.contains(roomNMD))){
                                insertTime(
                                    classInformation,
                                    i,
                                    classToTime(scom[1].replace(numToday(i), ""))[0],
                                    classToTime(scom[scom.size - 1].replace(numToday(i), ""))[1]
                                )
                            }
                            if (cntroom){
                                cntroom = false
                            }
                        }
                    }

                } catch (e: java.lang.Exception) {

                }
            }
        } catch (ex: Exception) {
            //Log.d("asdf", "nononon")
        }
    }
}
