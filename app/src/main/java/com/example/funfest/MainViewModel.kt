package com.example.funfest

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.funfest.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel: ViewModel() {
    lateinit var viewList: MutableList<ImageView>
    var lastSelectedCardModel: CardModel? = null
    var cardModelList = arrayListOf<CardModel>()
    val resIdList = listOf<Int>(
        R.drawable.wa,// 资源id的index 0
        R.drawable.hema,//              1
        R.drawable.huli,//              2
        R.drawable.yazi//               3
    )
    fun bind(binding: ActivityMainBinding) {
        viewList = mutableListOf(
            binding.iv11, binding.iv12, binding.iv13, binding.iv14,  // 0  1  2  3
            binding.iv21, binding.iv22, binding.iv23, binding.iv24, //  4  5  6  7
            binding.iv31, binding.iv32, binding.iv33, binding.iv34, //  8  9  10  11
            binding.iv41, binding.iv42, binding.iv43, binding.iv44  //  12  13 14 15
        )
        viewList.shuffle()
        for ((index, Id) in resIdList.withIndex()) {
            for (i in index*4..index*4+3) {
                val cardModel = CardModel(viewList[i],Id)
                cardModelList.add(cardModel)
            }
        }

    }
    fun cardClicked(view: View) {
        cardModelList.forEach {
            if (view == it.view) {
                // 无论什么时候点击，均会替换图片
                it.view.setImageResource(it.resId)
                if (lastSelectedCardModel == null) {
                    lastSelectedCardModel = it
                }
                else {
                    if (it.resId == lastSelectedCardModel!!.resId) {
                    // 线程切换，延迟翻面
                    Timer().schedule(object : TimerTask(){
                        override fun run() {
                            MainScope().launch {
                                // 两张图片相同，则都消失
                                it.view.visibility = View.INVISIBLE
                                lastSelectedCardModel!!.view.visibility = View.INVISIBLE
                                lastSelectedCardModel = null
                            }
                        } },
                        500)

                    }
                    else{
                        Timer().schedule(object : TimerTask(){
                            override fun run() {
                                MainScope().launch {
                                    // 两张图片不同，均回到初始状态
                                    it.view.setImageResource(R.drawable.icbg)
                                    lastSelectedCardModel!!.view.setImageResource(R.drawable.icbg)
                                    lastSelectedCardModel = null
                                }
                            }
                        },500)

                    }
                }
            }
        }
    }
}