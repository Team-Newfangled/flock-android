package com.example.kkirikkiri.view.recyclerview.project.projectpid

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kkirikkiri.R
import com.example.kkirikkiri.databinding.ItemPidBinding
import com.example.kkirikkiri.module.dto.NameRequest
import com.example.kkirikkiri.view.activity.project.pid.PidView
import com.example.kkirikkiri.view.recyclerview.pid.PidItem
import com.example.kkirikkiri.viewmodel.BoardModel

class ProjectPidAdapter(var list : List<PidItem>, private val intent: Intent, private val activity: Activity) : RecyclerView.Adapter<ProjectPidAdapter.Holder>(){

    private val pids = list

    inner class Holder(var binding : ItemPidBinding, intent: Intent, activity: Activity) : RecyclerView.ViewHolder(binding.root) {
        private val arrayItem = arrayOf("수정", "삭제")
        private val model = BoardModel()

        fun setPid(item : PidItem) {
            binding.pidId.text = item.id.toString()
            binding.itemPidTitle.text = item.title

            itemView.setOnClickListener {
                Intent(itemView.context, PidView::class.java)
                    .putExtra("title", item.title)
                    .putExtra("id", item.id)
                    .putExtra("writeId", item.writeId)
                    .run { itemView.context.startActivity(this) }
            }

            itemView.setOnLongClickListener {
                val dialog = AlertDialog.Builder(itemView.context)
                dialog.setItems(arrayItem) {dialog, pos ->
                    when (pos) {
                        0 -> {
                            dialog.dismiss()
                        }
                        1 -> {
                            model.deleteBoard(item.id)
                            refresh()
                            dialog.dismiss()
                        }
                    }
                }.show()
                false
            }
        }

        private fun refresh() {
            activity.finish()
            activity.overridePendingTransition(0,0)
            activity.startActivity(intent)
            activity.overridePendingTransition(0,0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemPidBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding, intent, activity)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val pid = pids[position]
        holder.setPid(pid)
    }

    override fun getItemCount(): Int {
        return pids.size
    }
}