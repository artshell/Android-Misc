package com.artshell.misc.rv.helper

/**
 * @author artshell on 2018/7/25
 */

private fun getErrorMessage(report: PagingRequestHelper.StatusReport): String {
    return PagingRequestHelper.RequestType.values().mapNotNull {
        report.getErrorFor(it)?.message
    }.first()
}


