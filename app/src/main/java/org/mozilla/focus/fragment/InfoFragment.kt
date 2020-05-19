/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package org.mozilla.focus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import mozilla.components.browser.session.Session

import org.mozilla.focus.R

class InfoFragment : WebFragment() {
    private var progressView: ProgressBar? = null
    private var webView: View? = null

    override val session: Session?
        get() = null

    override val initialUrl: String?
        get() = arguments?.getString(ARGUMENT_URL)

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        progressView = view.findViewById(R.id.progress)
        webView = view.findViewById(R.id.webview)
        val url = initialUrl
        if (url != null && !(url.startsWith("http://") || url.startsWith("https://"))) {
            // Hide webview until content has loaded, if we're loading built in about/rights/etc
            // pages: this avoid a white flash (on slower devices) between the screen appearing,
            // and the about/right/etc content appearing. We don't do this for SUMO and other
            // external pages, because they are both light-coloured, and significantly slower loading.
            webView?.visibility = View.INVISIBLE
        }
        applyLocale()
        return view
    }

    override fun onCreateViewCalled() {}

    companion object {
        private const val ARGUMENT_URL = "url"

        fun create(url: String): InfoFragment {
            val arguments = Bundle()
            arguments.putString(ARGUMENT_URL, url)
            val fragment = InfoFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
