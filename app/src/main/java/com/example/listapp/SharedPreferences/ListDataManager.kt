package com.example.listapp.SharedPreferences

import android.content.Context
import android.preference.PreferenceManager
import com.example.listapp.models.TaskList

class ListDataManager (val context: Context){

    fun saveList(list: TaskList){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPreferences.putStringSet(list.name, list.tasks.toHashSet())
        sharedPreferences.apply()
    }

    fun readLists(): ArrayList<TaskList>{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val sharedPreferenceContents = sharedPreferences.all
        val taskLists = ArrayList<TaskList>()

        for(preferenceItem in sharedPreferenceContents){
            val itemHashSet = preferenceItem.value as HashSet<String>
            val list = TaskList(preferenceItem.key, ArrayList(itemHashSet))
            taskLists.add(list)
        }

        return taskLists
    }
}