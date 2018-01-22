package com.duckydev.mvpdagger.data.source;

import com.duckydev.mvpdagger.data.Episode;
import com.duckydev.mvpdagger.util.EpisodeType;
import com.google.common.collect.Lists;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by duckyng on 12/26/2017.
 */
public class EpisodesRepositoryTest {

    private final static String TASK_TITLE = "title";
    private final static String TASK_TITLE2 = "title2";
    private final static String TASK_TITLE3= "title3";

    private static List<Episode> EPISODE = Lists.newArrayList(

            new Episode(1, 1, EpisodeType.ENGLISH_WE_SPEAK, "thumbImageUrl",
            "Title1", "episodeDate1", "Description1", "mediaUrl1", "6:50", "Summary1", "Transcript1",
            "todayHeadline1", "Vocabulary1", "Exercises1", "Answers1", "DownloadedMediaUrl1",
            true, false, false, false),

            new Episode(2, 2, EpisodeType.ENGLISH_WE_SPEAK, "thumbImageUrl",
                    "Title2", "episodeDate2", "Description2", "mediaUrl2", "3:22", "Summary2", "Transcript2",
                    "todayHeadline2", "Vocabulary2", "Exercises2", "Answers2", "DownloadedMediaUrl2",
                    true, false, false, true),

            new Episode(3, 3, EpisodeType.ENGLISH_WE_SPEAK, "thumbImageUrl",
            "Title3", "episodeDate3", "Description3", "mediaUrl3", "9:25", "Summary3", "Transcript3",
            "todayHeadline3", "Vocabulary3", "Exercises3", "Answers3", "DownloadedMediaUrl3",
            true, false, false, false)
            );

    private EpisodesRepository mEpisodesRepository;

    @Mock
    private EpisodesDataSource mEpisodesLocalDataSource;

    @Mock
    private EpisodesDataSource.GetEpisodeCallback mGetEpisodeCallback;

    @Mock
    private EpisodesDataSource.LoadEpisodesCallback mLoadEpisodesCallback;

    @Captor
    private ArgumentCaptor<EpisodesDataSource.LoadEpisodesCallback> mEpisodesCallbackArgumentCaptor;

    @Captor
    ArgumentCaptor<EpisodesDataSource.GetEpisodeCallback> mEpisodeCallbackArgumentCaptor;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        mEpisodesRepository = new EpisodesRepository(mEpisodesLocalDataSource);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getEpisodes_repositoryCachesAfterFirstApiCall(){
        // Given a setup Captor to capture callbacks
        //When two calls are issued to the episodes repository
        threeEpisodesLoadCallsToRepository(mLoadEpisodesCallback);

        // Then episodes were only requested once from Service API

        verify(mEpisodesLocalDataSource).getEpisodes(any(EpisodesDataSource.LoadEpisodesCallback.class));
    }

    @Test
    public void getTasks_requestAllTasksFromLoaclDataSource() {
        // When tasks are requested from the tasks repository
        mEpisodesRepository.getEpisodes(mLoadEpisodesCallback);

        // Then tasks are loaded from the local data source
        verify(mEpisodesLocalDataSource).getEpisodes(any(EpisodesDataSource.LoadEpisodesCallback.class));
    }

    @Test
    public void getEpisodesByType() throws Exception {
    }

    @Test
    public void getEpisode() throws Exception {
    }

    @Test
    public void getEpisodesByPlayState() throws Exception {
    }

    @Test
    public void getEpisodesByTypeAndPlayState() throws Exception {
    }

    @Test
    public void getDownloadedEpisodes() throws Exception {
    }

    @Test
    public void getFavoritedEpisodes() throws Exception {
    }

    @Test
    public void markDownloadedEpisodeMedia() throws Exception {
    }

    @Test
    public void markUndownloadedEpisodeMedia() throws Exception {
    }

    @Test
    public void updateDownloadedMediaUrl() throws Exception {
    }

    @Test
    public void markPlayedEpisode() throws Exception {
    }

    @Test
    public void markUnplayedEpisode() throws Exception {
    }

    @Test
    public void insertEpisode() throws Exception {
    }

    @Test
    public void updateEpisode() throws Exception {
    }

    @Test
    public void updateFavorite() throws Exception {
    }

    private void threeEpisodesLoadCallsToRepository(EpisodesDataSource.LoadEpisodesCallback callback) {
        // When epository are requested from repository
        mEpisodesRepository.getEpisodes(callback);

        // Use the Mockito Captor to capture the callback
        verify(mEpisodesLocalDataSource).getEpisodes(mEpisodesCallbackArgumentCaptor.capture());

        // Local data source doesn't have data yet
        mEpisodesCallbackArgumentCaptor.getValue().onDataNotAvailable();

        // Verify the remote data source is qureried
        verify(mEpisodesLocalDataSource).getEpisodes(mEpisodesCallbackArgumentCaptor.capture());

        //Trigger callback so episodes are cached
        mEpisodesCallbackArgumentCaptor.getValue().onEpisodesLoaded(EPISODE);

        mEpisodesRepository.getEpisodes(callback);
    }



    private void setEpisodesNotAvailable(EpisodesDataSource dataSource) {
        verify(dataSource).getEpisodes(mEpisodesCallbackArgumentCaptor.capture());
        mEpisodesCallbackArgumentCaptor.getValue().onDataNotAvailable();
    }

    private void setEpisodesAvailable(EpisodesDataSource dataSource, List<Episode> episodes) {
        verify(dataSource).getEpisodes(mEpisodesCallbackArgumentCaptor.capture());
        mEpisodesCallbackArgumentCaptor.getValue().onEpisodesLoaded(episodes);
    }

    private void setEpisodeNotAvailable(EpisodesDataSource dataSource, int taskId) {
        verify(dataSource).getEpisode(eq(taskId),mEpisodeCallbackArgumentCaptor.capture());
        mEpisodeCallbackArgumentCaptor.getValue().onDataNotAvailable();
    }

    private void setEpisodeAvailable(EpisodesDataSource dataSource, Episode episode) {
        verify(dataSource).getEpisode(eq(episode.get_id()), mEpisodeCallbackArgumentCaptor.capture());
        mEpisodeCallbackArgumentCaptor.getValue().onEpisodeLoaded(episode);
    }

}