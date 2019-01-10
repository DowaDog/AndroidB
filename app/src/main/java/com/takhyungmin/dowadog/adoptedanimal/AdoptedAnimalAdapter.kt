package com.takhyungmin.dowadog.adoptedanimal

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.takhyungmin.dowadog.R
import com.takhyungmin.dowadog.adoptedanimal.model.AdoptedAnimalObject
import com.takhyungmin.dowadog.adoptedanimal.model.get.Data
import com.takhyungmin.dowadog.interest.model.InterestAnimalObject

class AdoptedAnimalAdapter(var ctx: Context, var dataList: ArrayList<Data>): RecyclerView.Adapter<AdoptedAnimalAdapter.AdoptedAnimalViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdoptedAnimalViewHolder {
        var v : View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_mypage_act, p0, false)
        return AdoptedAnimalViewHolder(v)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: AdoptedAnimalViewHolder, position: Int) {

        holder.name.text = dataList[position].name
        holder.kind.text = dataList[position].kind
        holder.age.text = dataList[position].age.toString() + "살"

        if(dataList[position].gender == "M")
        {
            holder.sex.text = "수컷"
        }
        else{
            holder.sex.text = "암컷"
        }

        if (dataList[position].animalType == "개"){
            holder.dogOrCatIconimg.setImageResource(R.drawable.dog_icon_1227)
            holder.img.setImageResource(R.drawable.mypage_adopt_dog_img)
        }else {
            holder.dogOrCatIconimg.setImageResource(R.drawable.cat_icon_1227)
            holder.img.setImageResource(R.drawable.mypage_adopt_cat_img)
        }

        if(position%2==0){
            holder.root.setBackgroundResource(R.drawable.rv_item_adopted_animal_act_first_box)
        }else{
            holder.root.setBackgroundResource(R.drawable.rv_item_adopted_animal_act_second_box)
        }

        when (position) {
            0 -> {

            }
            1 -> {

            }
            2 -> {

            }
            3 -> {
                // 마지막 포지션 바텀에 여백주기
                val dp = ctx.resources.displayMetrics.density
                val layoutParams = holder.root.getLayoutParams() as RecyclerView.LayoutParams
                layoutParams.bottomMargin = (40 * dp).toInt()
                holder.root.layoutParams = layoutParams
            }
        }
        holder.root.setOnClickListener {
            // 입양한 동물 상세보기 액티비티로 넘어가기
            AdoptedAnimalObject.adoptedAnimalActivityPresenter.adoptedDetailResponseData(dataList[position].id)
        }
    }

    inner class AdoptedAnimalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var root : RelativeLayout = itemView.findViewById(R.id.rl_root_rv_item_mypage_act) as RelativeLayout
        var name: TextView = itemView.findViewById(R.id.tv_name_rv_item_mypage_act)
        var kind: TextView = itemView.findViewById(R.id.tv_kind_rv_item_mypage_act)
        var age: TextView = itemView.findViewById(R.id.tv_age_rv_item_mypage_act)
        var sex: TextView = itemView.findViewById(R.id.sex_rv_item_mypage_act)
        var img: ImageView = itemView.findViewById(R.id.iv_rv_item_mypage_act)
        var dogOrCatIconimg: ImageView = itemView.findViewById(R.id.iv_dog_or_cat_rv_item_mypage_act)
    }
}

data class AdoptedAnmalAdapterData (
        var name : String,
        var kind : String,
        var isDog : Int,
        var age : Int,
        var sex : String
)
