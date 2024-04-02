package com.example.musicx.di

import android.content.Context
import com.example.musicx.data.remote.MusicDatabaseImpl
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.C.USAGE_MEDIA
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {


    @ServiceScoped
    @Provides
    fun provideAudioAttributes() =
        AudioAttributes.Builder().setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).setUsage(USAGE_MEDIA)
            .build()


    @ServiceScoped
    @Provides
    fun provideMusicDatabase(firestoreStore: FirebaseFirestore) = MusicDatabaseImpl(firestoreStore)


    @Provides
    @ServiceScoped
    fun provideExoPlayer(
        @ApplicationContext context: Context, audioAttributes: AudioAttributes
    ) = SimpleExoPlayer.Builder(context).build().apply {
            setAudioAttributes(audioAttributes, true)
            setHandleAudioBecomingNoisy(true)
        }


    @ServiceScoped
    @Provides
    fun provideDataSourceFactory(
        @ApplicationContext context: Context
    ) = DefaultDataSourceFactory(
        context, Util.getUserAgent(context, "Spotify App")
    )
}