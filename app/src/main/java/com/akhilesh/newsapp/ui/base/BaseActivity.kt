package com.akhilesh.newsapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.akhilesh.newsapp.NewsApplication
import com.akhilesh.newsapp.di.component.ActivityComponent
import com.akhilesh.newsapp.di.component.DaggerActivityComponent
import com.akhilesh.newsapp.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<T : BaseViewModel<*>, viewBindingType : ViewBinding> :
    AppCompatActivity() {

    @Inject
    lateinit var viewModel: T

    private var _binding: viewBindingType? = null

    // Binding variable to be used for accessing views.
    protected val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        _binding = setupViewBinding(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setupView(savedInstanceState)
        setupObserver()
    }

    protected open fun setupObserver() {
    }

    private fun buildActivityComponent(): ActivityComponent = DaggerActivityComponent.builder()
        .applicationComponent((application as NewsApplication).applicationComponent)
        .activityModule(ActivityModule(this)).build()


    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    abstract fun setupView(savedInstanceState: Bundle?)

    abstract fun setupViewBinding(inflater: LayoutInflater):viewBindingType

    /*
    * Safe call method, just in case, if anything is messed up and lifecycle Event does not gets
    * called.
    */
    override fun onDestroy() {
        _binding = null
        super.onDestroy()

    }

}