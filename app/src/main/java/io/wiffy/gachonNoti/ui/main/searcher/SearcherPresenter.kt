package io.wiffy.gachonNoti.ui.main.searcher

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import com.github.eunsiljo.timetablelib.data.TimeData
import com.github.eunsiljo.timetablelib.data.TimeTableData
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.*
import io.wiffy.gachonNoti.model.ClassDataInformation
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.`object`.TimeCompare
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Document
import kotlin.collections.ArrayList
import java.util.*


class SearcherPresenter(private val mView: SearchContract.View) : SearchContract.Presenter {

    private var loadCount = 0
    private var errorCnt = 0
    private lateinit var findBuilding: ArrayList<String>
    private lateinit var findRoom: ArrayList<String>
    private lateinit var tableArr: ArrayList<ArrayList<TimeData<Any?>>>
    private lateinit var load1: String
    private lateinit var load2: String
    private lateinit var load3: String
    private lateinit var load4: String
    private lateinit var mView2: SearchContract.DialogPresenter

    override fun initPresent() {
        mView.initUI()
    }

    override fun initPresentDialog(tmp: SearchContract.DialogPresenter) {
        mView2 = tmp
    }

    override fun getData(yearSemester: String) {
        loadCount = 0
        errorCnt = 0
        Component.getBuilder()?.show()
        SearchAsyncTask("1", yearSemester, this).execute()
        SearchAsyncTask("2", yearSemester, this).execute()
        SearchAsyncTask("3", yearSemester, this).execute()
        SearchAsyncTask("4", yearSemester, this).execute()
    }

    override fun showLoad() {
        Component.getBuilder()?.show()
    }

    override fun dismissLoad() {
        loadCount += 1
        if (loadCount >= 4) {
            mView2.showBtn(false)
            mView2.requestLoad()
            Component.getBuilder()?.dismiss()
        }
    }

    override fun error() {
        errorCnt += 1
        if (errorCnt == 1) {
            Component.getBuilder()?.dismiss()
            mView2.errorDialog()
        }
    }

    override fun resetData() {
    }

    @SuppressLint("ApplySharedPref")
    override fun isDownloaded(year: String, semester: String) {

        val temp = if (Component.campus) {
            "global"
        } else {
            "medical"
        }

        load1 = getSharedItem(
            "$year-$semester-1-$temp", "<nodata>"
        )
        load2 = getSharedItem(
            "$year-$semester-2-$temp", "<nodata>"
        )
        load3 = getSharedItem(
            "$year-$semester-3-$temp", "<nodata>"
        )
        load4 = getSharedItem(
            "$year-$semester-4-$temp", "<nodata>"
        )

        if (!load1.contains("<nodata>") &&
            !load2.contains("<nodata>") &&
            !load3.contains("<nodata>") &&
            !load4.contains("<nodata>")
        ) {
            mView2.showBtn(false)
            Handler(Looper.getMainLooper()).post {
                mView2.categoryInvisible()
            }
            findBuilding = ArrayList()
            Thread(Runnable {
                findBuildingXML(load1)
                findBuildingXML(load2)
                findBuildingXML(load3)
                findBuildingXML(load4)
                findBuilding.sort()
                Handler(Looper.getMainLooper()).post {
                    mView2.setSpinner(findBuilding)
                }
            }).start()
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
        }

    }

    override fun loadRoom(roomNM: String) {
        Thread(Runnable {
            findRoom = ArrayList()
            findRoomXML(load1, roomNM)
            findRoomXML(load2, roomNM)
            findRoomXML(load3, roomNM)
            findRoomXML(load4, roomNM)
            findRoom.sort()
            Handler(Looper.getMainLooper()).post {
                mView2.setListDialog(findRoom)
                Component.getBuilder()?.dismiss()
            }
        }).start()

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
        }
    }

    override fun loadTable(str: String) {
        Handler(Looper.getMainLooper()).post {
            Component.getBuilder()?.show()
        }
        Thread(Runnable {
            tableArr = ArrayList(6)
            with(tableArr)
            {
                add(ArrayList())
                add(ArrayList())
                add(ArrayList())
                add(ArrayList())
                add(ArrayList())
                add(ArrayList())
            }

            findTable(load1, str)
            findTable(load2, str)
            findTable(load3, str)

            //Thx rurimo!
            for (num in 0..4) {
                Collections.sort(tableArr[num], TimeCompare)
            }

            val array2 = ArrayList<TimeTableData>().apply {
                add(TimeTableData("월", tableArr[0]))
                add(TimeTableData("화", tableArr[1]))
                add(TimeTableData("수", tableArr[2]))
                add(TimeTableData("목", tableArr[3]))
                add(TimeTableData("금", tableArr[4]))
            }

            Handler(Looper.getMainLooper()).post {
                mView.setTimeTable(array2, str)
                Component.getBuilder()?.dismiss()
            }
        }).start()
    }


    private fun insertTime(str1: ClassDataInformation, cnt: Int, start: Long, end: Long) {
        tableArr[cnt].add(
            TimeData(
                2,
                str1.name,
                getRandomColorId(),
                R.color.white,
                start,
                end
            )
        )
    }

    private fun numToday(num: Int): String = when (num) {
        0 -> "월"
        1 -> "화"
        2 -> "수"
        3 -> "목"
        4 -> "금"
        else -> "토"
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
                    val mData = Array(5) { "" }
                    for (t in time) {
                        if (t.contains("월")) {
                            mData[0] = "${mData[0]},$t"
                        }
                        if (t.contains("화")) {
                            mData[1] = "${mData[1]},$t"
                        }
                        if (t.contains("수")) {
                            mData[2] = "${mData[2]},$t"
                        }
                        if (t.contains("목")) {
                            mData[3] = "${mData[3]},$t"
                        }
                        if (t.contains("금")) {
                            mData[4] = "${mData[4]},$t"
                        }
                    }
                    for (i in 0..4) {
                        if (mData[i].contains(",")) {
                            val sCom = mData[i].replace(" ", "").split(",")
                            if ((cntroom && tempRoom.contains(roomNMD)) ||
                                (!cntroom && tempRoom2.contains(roomNMD))
                            ) {
                                insertTime(
                                    classInformation,
                                    i,
                                    classToTime(sCom[1].replace(numToday(i), ""))[0],
                                    classToTime(sCom[sCom.size - 1].replace(numToday(i), ""))[1]
                                )
                            }
                            if (cntroom) {
                                cntroom = false
                            }
                        }
                    }

                } catch (e: java.lang.Exception) {

                }
            }
        } catch (ex: Exception) {
        }
    }
}
