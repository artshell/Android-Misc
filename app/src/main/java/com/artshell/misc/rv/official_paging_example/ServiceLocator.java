package com.artshell.misc.rv.official_paging_example;

import android.content.Context;
import android.support.annotation.GuardedBy;
import android.support.annotation.VisibleForTesting;

import com.artshell.misc.rv.official_paging_example.api.RedditApi;
import com.artshell.misc.rv.official_paging_example.db.RedditDb;
import com.artshell.misc.rv.official_paging_example.repository.RedditPostRepository;
import com.artshell.misc.rv.official_paging_example.repository.inDb.DbRedditPostRepository;
import com.artshell.misc.rv.official_paging_example.repository.inMemory.byItem.InMemoryByItemRepository;
import com.artshell.misc.rv.official_paging_example.repository.inMemory.byPage.InMemoryByPageKeyRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Super simplified service locator implementation to allow us to replace default implementations
 * for testing.
 */
public abstract class ServiceLocator {
    private static final Object LOCK = new Object();
    private static volatile ServiceLocator instance;

    public static ServiceLocator instance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new DefaultServiceLocator(context.getApplicationContext(), false);
                }
            }
        }
        return instance;
    }

    /**
     * Allows tests to replace the default implementations.
     */
    @VisibleForTesting
    public void swap(ServiceLocator locator) {
        synchronized (LOCK) {
            instance = locator;
        }
    }

    public abstract RedditPostRepository getRepository(RedditPostRepository.Type type);

    public abstract Executor getNetworkExecutor();

    public abstract Executor getDiskIOExecutor();

    public abstract RedditApi getRedditApi();

    /**
     * default implementation of ServiceLocator that uses production endpoints.
     */
    public static class DefaultServiceLocator extends ServiceLocator {
        private static final Object LOCK = new Object();
        private static final Executor DISK_IO = Executors.newSingleThreadExecutor();
        private static final Executor NETWORK_IO = Executors.newSingleThreadExecutor();
        private Context app;
        private boolean useInMemoryDb;

        @GuardedBy("LOCK")
        private static volatile RedditDb db;
        @GuardedBy("LOCK")
        private static volatile RedditApi api;

        DefaultServiceLocator(Context app, boolean useInMemoryDb) {
            this.app = app;
            this.useInMemoryDb = useInMemoryDb;
        }

        private RedditDb lazyDb() {
            if (db == null) {
                synchronized (LOCK) {
                    if (db == null) {
                        db = RedditDb.create(app, useInMemoryDb);
                    }
                }
            }
            return db;
        }

        private RedditApi lazyApi() {
            if (api == null) {
                synchronized (LOCK) {
                    if (api == null) {
                        api = RedditApiCreator.create();
                    }
                }
            }
            return api;
        }

        @Override
        public RedditPostRepository getRepository(RedditPostRepository.Type type) {
            switch (type) {
                case IN_MEMORY_BY_ITEM:
                    return new InMemoryByItemRepository(getRedditApi(), getNetworkExecutor());
                case IN_MEMORY_BY_PAGE:
                    return new InMemoryByPageKeyRepository(getRedditApi(), getNetworkExecutor());
                default:
                    return new DbRedditPostRepository(lazyDb(), getRedditApi(), getDiskIOExecutor());
            }
        }

        @Override
        public Executor getNetworkExecutor() {
            return NETWORK_IO;
        }

        @Override
        public Executor getDiskIOExecutor() {
            return DISK_IO;
        }

        @Override
        public RedditApi getRedditApi() {
            return lazyApi();
        }
    }
}
