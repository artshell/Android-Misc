package com.artshell.misc.rv.official_paging_example.repository.inDb

import com.artshell.misc.rv.official_paging_example.api.RedditApi
import com.artshell.misc.rv.official_paging_example.db.RedditDb
import com.artshell.misc.rv.official_paging_example.db.RedditPost
import com.artshell.misc.rv.official_paging_example.repository.Listing
import com.artshell.misc.rv.official_paging_example.repository.RedditPostRepository
import java.util.concurrent.Executor

class DbRedditPostRepository(
        val db: RedditDb,
        private val redditApi: RedditApi,
        private val ioExecutor: Executor,
        private val networkPageSize: Int = DEFAULT_NETWORK_PAGE_SIZE) : RedditPostRepository {

    companion object {
        private const val DEFAULT_NETWORK_PAGE_SIZE = 10
    }

    /**
     * Inserts the response into the database while also assigning position indices to items.
     */
    private fun insertResultIntoDb(subredditName: String, body: RedditApi.ListingResponse?) {
        body!!.data.children.let { posts ->
            db.runInTransaction {
                val start = db.posts().getNextIndexInSubreddit(subredditName)
                val items = posts.mapIndexed { index, child ->
                    child.post.indexInResponse = start + index
                    child.post
                }
                db.posts().insert(items)
            }
        }
    }

    override fun postsSubreddit(subReddit: String?, pageSize: Int): Listing<RedditPost> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}