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
import kotlin.math.absoluteValue
import java.util.*
import java.util.Objects.compare
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
            mView.showBtn(false)
            findBuilding = ArrayList()
            findBuildingXML(load1)
            findBuildingXML(load2)
            findBuildingXML(load3)
        } else {
            mView.showBtn(true)
        }
    }

    private fun findBuildingXML(data: String) {
        try {
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val sr = StringReader(data.replace("<?xml version='1.0' encoding='EUC-KR'?>", ""))
            val doc: Document = builder.parse(InputSource(sr))
            val nodeList = doc.getElementsByTagName("grid")
//          Log.d("asdf", doc.xmlEncoding)
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
                    //Log.d("asdf",classInformation.name)
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
//                  Log.d("asdf", "${i.toString()}$roomNM")
                } catch (e: java.lang.Exception) {
//                    Log.d("asdf","wawawa")
                }
            }
        } catch (ex: Exception) {
            Log.d("asdf", "nononon")
        }
        findBuilding.sort()
        mView.setSpinner(findBuilding)
    }

    override fun loadRoom(roomNM: String) {
        findRoom = ArrayList()
        findRoomXML(load1, roomNM)
        findRoomXML(load2, roomNM)
        findRoomXML(load3, roomNM)
        findRoom.sort()
        mView.setlistDialog(findRoom)
    }

    private fun findRoomXML(data: String, roomNMD: String) {
        //Log.d("asdf",roomNMD)
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
                    //Log.d("asdf",classInformation.name)
                    val roomNM = classInformation.room.split(",")
                    for (x in roomNM) {
                        if (!findRoom.contains(x) && x.contains(roomNMD)) {
                            findRoom.add(x)
                        }
                    }
//                  Log.d("asdf", "${i.toString()}$roomNM")
                } catch (e: java.lang.Exception) {
//                    Log.d("asdf","wawawa")
                }
            }
        } catch (ex: Exception) {
            Log.d("asdf", "nononon")
        }
    }


    class timecompare : Comparator<TimeData<Any?>> {
        override fun compare(o1: TimeData<Any?>?, o2: TimeData<Any?>?): Int {
            return o1!!.startMills.compareTo(o2!!.startMills);
        }
    }

    fun loadTable(str: String) {
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

        Collections.sort(tablearr[0],timecompare())
        Collections.sort(tablearr[1],timecompare())
        Collections.sort(tablearr[2],timecompare())
        Collections.sort(tablearr[3],timecompare())
        Collections.sort(tablearr[4],timecompare())

        var array2 = ArrayList<TimeTableData>()
        array2.add(TimeTableData("월", tablearr[0]))
        array2.add(TimeTableData("화", tablearr[1]))
        array2.add(TimeTableData("수", tablearr[2]))
        array2.add(TimeTableData("목", tablearr[3]))
        array2.add(TimeTableData("금", tablearr[4]))

        for (i in tablearr[4]) {
            Log.d("asdf", i.title + "---")
        }

        mView.setTimeTable(array2)
    }


    private fun insertTime(str1: ClassDataInformation, str2: String, cnt: Int, start: Long, end: Long) {
        tablearr[cnt].add(
            TimeData(
                2,
                str1.name,
                R.color.mainBlue,
                R.color.white,
                start,
                end
            )
        )

        //Log.d("asdf", str1.name + cnt + "-" + start / 60000 + "-" + end / 60000)
    }

    private fun numToday(num: Int): String {
        return when (num) {
            0 -> {
                "월"
            }
            1 -> {
                "화"
            }
            2 -> {
                "수"
            }
            3 -> {
                "목"
            }
            4 -> {
                "금"
            }
            else -> {
                "토"
            }
        }
    }

    private fun findTable(data: String, roomNMD: String) {
        //Log.d("asdf",roomNMD)
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
                    val room = firstElement.getElementsByTagName("room")
                    val classInformation = ClassDataInformation(
                        firstElement.getElementsByTagName("subjectNm").item(0).childNodes.item(0).nodeValue,
                        firstElement.getElementsByTagName("time").item(0).childNodes.item(0).nodeValue,
                        room.item(0).childNodes.item(0).nodeValue
                    )

                    var cntroom = 0
                    var tmproom = classInformation.room.replace(" ", "")
                    var tmproom2 = classInformation.room.replace(" ", "")
                    if (classInformation.room.contains(",")) {
                        tmproom = tmproom.split(",")[0]
                        tmproom2 = tmproom2.split(",")[1]
                    }

                    val time = classInformation.time.split(",")
                    var data = Array(5) { "" }
                    for (t in time) {
                        if (t.contains("월")) {
                            data[0] = "${data[0]},$t"
                        }
                        if (t.contains("화")) {
                            data[1] = "${data[1]},$t"
                        }
                        if (t.contains("수")) {
                            data[2] = "${data[2]},$t"
                        }
                        if (t.contains("목")) {
                            data[3] = "${data[3]},$t"
                        }
                        if (t.contains("금")) {
                            data[4] = "${data[4]},$t"
                        }
                    }
                    for (i in 0..4) {
                        if (data[i].contains(",")) {
                            var scom = data[i].replace(" ", "").split(",")

                            if (cntroom == 0) {
                                cntroom += 1

                                if (tmproom.contains(roomNMD)) {
                                    if (classInformation.name.contains("모바일") || classInformation.name.contains("데이터")) {
//                                        Log.d("asdf",classInformation.name)
//                                        Log.d("asdf",tmproom)
//                                        Log.d("asdf",roomNMD)
//                                        Log.d("asdf",data[i])
                                    }
                                    insertTime(
                                        classInformation,
                                        roomNMD,
                                        i,
                                        classToTime(scom[1].replace(numToday(i), ""))[0],
                                        classToTime(scom[scom.size - 1].replace(numToday(i), ""))[1]
                                    )
                                }
                            } else {
                                if (tmproom2.contains(roomNMD)) {
                                    insertTime(
                                        classInformation,
                                        roomNMD,
                                        i,
                                        classToTime(scom[1].replace(numToday(i), ""))[0],
                                        classToTime(scom[scom.size - 1].replace(numToday(i), ""))[1]
                                    )
                                }
                            }
                        }
                    }

//                  Log.d("asdf", "${i.toString()}$roomNM")
                } catch (e: java.lang.Exception) {
//                    Log.d("asdf","wawawa")
                }
            }
        } catch (ex: Exception) {
            Log.d("asdf", "nononon")
        }
    }
}
