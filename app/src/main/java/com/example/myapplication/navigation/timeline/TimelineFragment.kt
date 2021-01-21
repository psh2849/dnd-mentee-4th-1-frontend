/**
 *  하단 탭에서 List 선택시 보여지는 Fragment
 *
 *  게시글을 서버에서 받아서 리싸이클러뷰에 뿌려준다.
 */

package com.example.myapplication.navigation.timeline

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.data.datasource.remote.api.RecipeDTO
import com.example.myapplication.data.repository.RepositoryImpl

class TimelineFragment : Fragment(), TimelineRecyclerInterface {

    private lateinit var v: View

    private var list = ArrayList<RecipeDTO.PostItem>()

    private lateinit var myAdapter: TimelineRecyclerAdapter

    private val repository = RepositoryImpl()

    private var list2 = ArrayList<RecipeDTO.PostItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list, container, false)

        callRecycler()
        return v
    }

    /**
     *  fragment 생성시 리사이클러 호출해주는 함수
     */
    fun callRecycler() {
        list.clear()
        /*
        list.add(
            RecipeDTO.TimelineResponse(
                null,
                null,
                null,
                1,
                null,
                null,
                "여기는 부제목",
                "여기는 제목!"
            )
        )

        list.add(
            RecipeDTO.TimelineResponse(
                null,
                null,
                null,
                2,
                null,
                null,
                "여기는 subtitle",
                "여기는 title!"
            )
        )
         */
        /**
         * API 통신 확인을 위해 추가
         *              - 함도영
         * */
        repository.getAllTimelineList(
            success = {
                it.run {
                    Log.d("it", it.toString())
                    // list.addAll()
                }
            },
            fail = {
                Log.d("fail", "failfailfail")
            }
        )
        myAdapter = TimelineRecyclerAdapter(this)
        myAdapter.updateTimelineList(list)
        myAdapter.notifyDataSetChanged()

        var rv_list = v.findViewById(R.id.rv_list) as RecyclerView
        rv_list.adapter = myAdapter
    }

    override fun onItemClicked(position: Int) {
        Log.d("로그", "TimeLinFragment - 클릭됨")
//        Toast.makeText(
//            App.instance,
//            "id : ${this.list[position].id}\n Title : ${this.list[position].title}\n subTitle: ${this.list[position].subTitle}",
//            Toast.LENGTH_SHORT
//        ).show()

    }
}