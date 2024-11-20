package com.gurmeet.alllanguagenewsapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.gurmeet.alllanguagenewsapp.Application
import com.gurmeet.alllanguagenewsapp.utils.NetworkViewModel
import com.gurmeet.alllanguagenewsapp.utils.NetworkViewModelFactory


// A generic base activity that uses View Binding

abstract class BaseActivity<VB : ViewBinding,VM : ViewModel> : AppCompatActivity() {
    private var toastCounterAvailable = 0 // Counter to track the number of Toasts shown
    private var toastCounterNotAvailable=0
    private val maxToasts = 3 // Limit to how many times we show the Toast
    // Declare the binding variable
    private var _binding: VB? = null
    protected lateinit var  networkViewModel: NetworkViewModel
    protected val binding get() = _binding!!
   // protected lateinit var viewModel: VM
    // Nullable ViewModel reference
    protected var viewModel: VM? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = inflateBinding(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (viewModel != null) {
            viewModel = ViewModelProvider(this).get(getViewModelClass())
        }
        val factory = NetworkViewModelFactory(application as Application)
        networkViewModel = ViewModelProvider(this, factory).get(NetworkViewModel::class.java)
        networkViewModel.startMonitoring()
       // viewModel = ViewModelProvider(this).get(getViewModelClass())
    }

    // Abstract method that must be implemented by subclasses to return the appropriate binding class
    protected abstract fun inflateBinding(inflater: LayoutInflater): VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null  // Avoid memory leaks by setting the binding to null
    }

    protected abstract fun getViewModelClass(): Class<VM>
   protected fun resetToastCounter() {
       toastCounterAvailable = 0
       toastCounterNotAvailable=0
    }
    protected fun toastInternetAvailable(maxTimes:Int){
        if (toastCounterAvailable < maxTimes) {
            Toast.makeText(this, "Internet is available", Toast.LENGTH_SHORT).show()
            toastCounterAvailable++ // Increment the counter
        }

    }
    protected fun toastInternetNotAvailable(maxTimes: Int){
        if (toastCounterNotAvailable < maxTimes) {
            Toast.makeText(this, "Internet is not available", Toast.LENGTH_SHORT).show()
            toastCounterNotAvailable++ // Increment the counter
        }
    }
     open fun addScaleEffectToButton(button: Button) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Zoom In (scale down effect)
                    v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(150).start()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Zoom Out (scale back to original size)
                    v.animate().scaleX(1f).scaleY(1f).setDuration(150).start()
                }
            }
            false
        }
    }

}


