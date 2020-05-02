package com.devlopersquad.papacodes.data.cache

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.devlopersquad.papacodes.domain.model.DomainCodeModel

class CodeCacheImpl(private val context: Context) : CodeCache {
    private var dataCodeModel: DomainCodeModel = DomainCodeModel()

    override suspend fun get(): DomainCodeModel = dataCodeModel

    override suspend fun put(codeModel: DomainCodeModel) {
        dataCodeModel = codeModel
    }

    override suspend fun isCached(): Boolean = dataCodeModel.codes.isNotEmpty()

    override suspend fun storeCopiedCode(code: String): String {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("simple text", code)
        clipboardManager.setPrimaryClip(clip)
        return code
    }
}