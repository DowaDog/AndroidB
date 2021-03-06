package com.takhyungmin.dowadog.adopt.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.jakewharton.rxbinding2.view.clicks
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.adopt.AdoptObject
import com.takhyungmin.dowadog.adopt.model.get.UrgentAnimalData
import com.takhyungmin.dowadog.presenter.fragment.AdoptAnimalFindFragmentPresenter

class AnimalFindUrgentAdapter(var urgentItems : ArrayList<UrgentAnimalData>,
                              var urgentRequestManager : RequestManager, var adoptAnimalFindFragmentPresenter: AdoptAnimalFindFragmentPresenter) : RecyclerView.Adapter<AnimalFindUrgentViewHolder>() {

    var width = 0
    var height = 0

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): AnimalFindUrgentViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_urgent_animal_act_box, parent, false)
        height = ((parent.measuredWidth / 2) * 1.16).toInt()

        return AnimalFindUrgentViewHolder(view)
    }

    override fun getItemCount(): Int = urgentItems.size

    override fun onBindViewHolder(holder: AnimalFindUrgentViewHolder, position: Int) {
        urgentRequestManager.load(urgentItems[position].ani_img).into(holder.ani_img)
        holder.ani_region.text = urgentItems[position].ani_region
        holder.tv_ani_kind.text = urgentItems[position].ani_kind
        holder.d_day.text = urgentItems[position].d_day

        if(urgentItems[position].ani_gender == "F")
            holder.ani_gender.setImageResource(R.drawable.woman_icon_1227)
        else
            holder.ani_gender.setImageResource(R.drawable.man_icon_1227)
//        holder.ani_img.clicks().subscribe {
//            adoptAnimalFindFragmentPresenter.toApply()
//        }

        holder.ani_img.setClipToOutline(true)

        val params = holder.newFrame.layoutParams
        params.height = height
        holder.newFrame.layoutParams = params

        holder.newFrame.clicks().subscribe {
            AdoptObject.adoptAnimalFindFragmentPresnter.toDtail(urgentItems[position].id)
        }

        if(urgentItems[position].liked)
            urgentRequestManager.load(R.drawable.heart_red_1227).into(holder.heart)
        else
            urgentRequestManager.load(R.drawable.heart_gray_1227).into(holder.heart)

    }
}
