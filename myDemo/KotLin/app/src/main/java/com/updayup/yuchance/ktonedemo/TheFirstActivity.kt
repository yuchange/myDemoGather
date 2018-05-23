package com.updayup.yuchance.ktonedemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by yuchance on 2018/5/11.
 */
public class TheFirstActivity : AppCompatActivity(){




    var ABS = "ABS"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        max(3,5)

        if (max(4,6)==7){

        }


    }
    fun main(args:Array<String>){

    }
    fun max(a:Int,b:Int):Int{
        return if (a>b)a else b
    }




}