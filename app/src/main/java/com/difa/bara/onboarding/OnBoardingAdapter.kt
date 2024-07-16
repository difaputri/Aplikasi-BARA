package com.difa.bara.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.difa.bara.databinding.ItemOnboardingBinding

class OnBoardingAdapter(private val context: Context):
    RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>()
{
    private lateinit var binding: ItemOnboardingBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnBoardingAdapter.OnBoardingViewHolder {
        binding = ItemOnboardingBinding.inflate(LayoutInflater.from(context), parent, false)
        return OnBoardingViewHolder()
    }

    override fun onBindViewHolder(holder: OnBoardingAdapter.OnBoardingViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class OnBoardingViewHolder: ViewHolder(binding.root) {
        fun bind(data: OnBoardingModel) {
            with(binding) {
                ivOnboarding.setImageResource(data.image)
                tvTitle.text = data.title
                tvDesc.text = data.desc
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<OnBoardingModel>() {
        override fun areItemsTheSame(oldItem: OnBoardingModel, newItem: OnBoardingModel): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: OnBoardingModel, newItem: OnBoardingModel): Boolean = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}