package com.example.dailycat

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide // 引入Glide库
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // 1. 找到我们在界面上放的两个东西
        // findViewById 就像是点名，找到刚才命名的 iv_cat 和 btn_get_cat
        val catImage = findViewById<ImageView>(R.id.iv_cat)
        val getButton = findViewById<Button>(R.id.btn_get_cat)

        // 2. 这里的 API 地址是一个免费的随机猫咪图片接口
        // 为了防止 Glide 觉得网址没变就不刷新，我们加个时间戳 "?t=..." 骗它这是一张新图
        val BaseUrl = "https://picsum.photos/300/300"

        // 3. 给按钮设置点击事件（监听器）
        getButton.setOnClickListener {
            // 每次生成一个随机数拼在后面
            val currentUrl = "$BaseUrl?random=${System.currentTimeMillis()}"

            // 4. 使用 Glide 加载图片
            Glide.with(this)
                .load(currentUrl)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(android.R.drawable.stat_sys_download)
                .into(catImage)
        }

        // 5. 刚打开App时，自动先点一次按钮，加载第一张图
        getButton.performClick()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}