package com.takhyungmin.dowadog.scrap

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.interest.model.InterestAnimalObject
import com.takhyungmin.dowadog.presenter.activity.ScrapActivityPresenter
import com.takhyungmin.dowadog.scrap.scrapmodel.ScrapObject
import com.takhyungmin.dowadog.scrap.scrapmodel.get.GetMyScrapData

class ScrapRecyclerViewAdapter(var ctx: Context, var dataList: ArrayList<GetMyScrapData>) : RecyclerView.Adapter<ScrapRecyclerViewAdapter.ScrapRecyclerViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ScrapRecyclerViewHolder {
        var v : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_scrap_act, p0, false)

        return ScrapRecyclerViewHolder(v)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ScrapRecyclerViewHolder, position: Int) {


//            holder.btn.setOnClickListener {
//                // 컨텐츠디테일 액티비티로 넘어가기
//            }




        holder.title.text = dataList[position].title

        holder.date.text = dataList[position].createdAt.substring(0, 10)

        holder.btn.setOnClickListener {
            // 컨텐츠디테일 액티비티로 넘어가기
            ScrapObject.scrapActivityPresenter.scrapContentsDetail(dataList[position].id, dataList[position].type, dataList[position].title)

//            var intent = Intent(ctx, DogDetailActivity::class.java)
//            intent.putExtra("animalId", dataList[position].id)
//            (ctx as Activity).startActivityForResult(intent, 6900)
        }

    }

    inner class ScrapRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var btn: RelativeLayout = itemView.findViewById(R.id.rl_all_box_scrap_act)
        var title: TextView = itemView.findViewById(R.id.tv_title_scrap_act)
        var date: TextView = itemView.findViewById(R.id.tv_date_scrap_act)
    }
}