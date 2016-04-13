#ifndef THREAD_HPP_DEF
#define THREAD_HPP_DEF

#include <pthread.h>

class kinectMutex {
    public:
        kinectMutex();
        void lock();
        void unlock();
        pthread_mutex_t *get();
    private:
        pthread_mutex_t m_mutex;
};

class kinectCondition {
    public:
        kinectCondition();
        void signal();
        void signal_all();
        void wait();
        void lock();
        void unlock();
    private:
        pthread_cond_t m_cond;
        kinectMutex mtx;
};

#endif