package com.gurmeet.alllanguagenewsapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding



// A generic base activity that uses View Binding

abstract class BaseActivity<VB : ViewBinding,VM : ViewModel> : AppCompatActivity() {

    // Declare the binding variable
    private var _binding: VB? = null
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
       // viewModel = ViewModelProvider(this).get(getViewModelClass())
    }

    // Abstract method that must be implemented by subclasses to return the appropriate binding class
    protected abstract fun inflateBinding(inflater: LayoutInflater): VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null  // Avoid memory leaks by setting the binding to null
    }

    protected abstract fun getViewModelClass(): Class<VM>
}


