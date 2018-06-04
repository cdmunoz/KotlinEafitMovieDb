package co.cdmunoz.kotlineafitmoviedb;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import co.cdmunoz.kotlineafitmoviedb.data.MovieItem;
import co.cdmunoz.kotlineafitmoviedb.data.source.local.MoviesDatabase;
import co.cdmunoz.kotlineafitmoviedb.data.source.local.MoviesDbDao;
import io.reactivex.functions.Predicate;

@RunWith(AndroidJUnit4.class)
public class MoviesDbDaoTest {

    private MoviesDatabase moviesDatabase;
    private MoviesDbDao moviesDbDao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb(){
        //use in memory db. The data will be deleted after the process is killed
        moviesDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), MoviesDatabase.class)
                .allowMainThreadQueries()//only for testing purposes
                .build();
        moviesDbDao = moviesDatabase.moviesDbDao();
    }

    @After
    public void closeDb(){
        moviesDatabase.close();
    }

    @Test
    public void getMoviesWhenNoMoviesInserted() {
        moviesDbDao.queryMovies()
                .test()
                .assertValue(new Predicate<List<MovieItem>>() {
                    @Override
                    public boolean test(List<MovieItem> itemList) throws Exception {
                        return itemList.size() == 0;
                    }
                });
    }

    @Test
    public void insertMovie_SuccessfullyInsertMovie(){
        final MovieItem movieItem = populateMovieItemWithData();
        moviesDbDao.insertMovie(movieItem);
        moviesDbDao.queryMovies().test().assertValue(new Predicate<List<MovieItem>>() {
            @Override
            public boolean test(List<MovieItem> itemList) throws Exception {
                return itemList.size() > 0 && itemList.get(0).getId() == movieItem.getId();
            }
        });
        moviesDbDao.deleteAllMovies();//to allow running new tests with the initial conditions
    }

    private MovieItem populateMovieItemWithData() {
        List<Integer> genreIds = new ArrayList<Integer>();
        genreIds.add(1);
        genreIds.add(2);
        genreIds.add(3);
        return new MovieItem(1, "Overview", "EN", "Title", false, "Title",
                genreIds, "path_poster", "back_path", "2018-09-21", 8.0,
                123.0,false, 8);
    }
}
