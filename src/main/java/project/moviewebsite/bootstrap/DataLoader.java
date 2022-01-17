package project.moviewebsite.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.moviewebsite.models.*;
import project.moviewebsite.services.ActorService;
import project.moviewebsite.services.CategoryService;
import project.moviewebsite.services.CountryService;
import project.moviewebsite.services.MovieService;

/**
 * This class loads the original data of the website.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final MovieService movieService;
    private final CountryService countryService;
    private final CategoryService categoryService;
    private final ActorService actorService;

    /**
     * Construct a DataLoader with the given movieService, countryService, categoryService and actorService.
     * @param movieService
     * @param countryService
     * @param categoryService
     * @param actorService
     */
    public DataLoader(MovieService movieService, CountryService countryService, CategoryService categoryService, ActorService actorService) {
        this.movieService = movieService;
        this.countryService = countryService;
        this.categoryService = categoryService;
        this.actorService = actorService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    /**
     * Load the data for the website.
     */
    private void loadData() {
        Category drama = new Category("Drama");
        Category comedy = new Category("Comedy");
        Category animation = new Category("Animation");
        Category adventure = new Category("Adventure");
        Category action = new Category("Action");
        Category biography = new Category("Biography");
        Category family = new Category("Family");
        Category history = new Category("History");
        Category thriller = new Category("Thriller");
        Category romance = new Category("Romance");
        categoryService.save(drama);
        categoryService.save(comedy);
        categoryService.save(animation);
        categoryService.save(adventure);
        categoryService.save(action);
        categoryService.save(biography);
        categoryService.save(family);
        categoryService.save(history);
        categoryService.save(thriller);
        categoryService.save(romance);
        System.out.println("Load categories...");

        Country korea = new Country("Korea");
        Country america = new Country("America");
        Country canada = new Country("Canada");
        Country japan = new Country("Japan");
        Country france = new Country("France");
        Country germany = new Country("Germany");
        countryService.save(korea);
        countryService.save(america);
        countryService.save(canada);
        countryService.save(japan);
        countryService.save(france);
        countryService.save(germany);
        System.out.println("Load countries...");

        Actor shim = new Actor("Eun-kyung Shim", 1994, 5, 31, Gender.FEMALE, korea);
        Actor min = new Actor("Hyo-rin Min", 1986, 2, 28, Gender.FEMALE, korea);
        Actor kang = new Actor("So-Ra Kang", 1990, 2, 18, Gender.FEMALE, korea);
        actorService.save(shim);
        actorService.save(min);
        actorService.save(kang);

        String description = "Seven girls become good friends in high school, then events pull them apart " +
                             "for 25 years. When one of the friends lies dying in a hospital, she wishes " +
                             "to see each of them one last time.";
        String review = "They cried together, laughed together, did crazy things and made trouble together. " +
                        "Even when they get old, they still have the most beautiful and best youth to " +
                        "commemorate together.";
        Movie sunny = new Movie("Sunny", 135, 9.0, 2011, 5, 4, description, review);
        sunny.addCategory(drama);
        sunny.addCategory(comedy);
        sunny.addCountry(korea);
        sunny.addActor(shim);
        sunny.addActor(min);
        sunny.addActor(kang);
        sunny.loadPoster("sunny.jpg");
        sunny.setUrl("https://play.google.com/store/movies/details?id=OrHwrImhgww");
        movieService.save(sunny);

        Actor foxx = new Actor("Jamie Foxx", 1967, 12, 13, Gender.MALE, america);
        Actor fey = new Actor("Tina Fey", 1970, 5, 18, Gender.FEMALE, america);
        Actor rashad = new Actor("Phylicia Rashad", 1948, 6, 19, Gender.FEMALE, america);
        actorService.save(foxx);
        actorService.save(fey);
        actorService.save(rashad);

        description = "Joe is a middle-school band teacher whose life hasn't quite gone the way he expected. " +
                      "His true passion is jazz -- and he's good. But when he travels to another realm to " +
                      "help someone find their passion, he soon discovers what it means to have soul.";
        review = "The fallen leaves from the branches, the exhaust air blowing in the street, the singing " +
                 "in the busy crowd in the subway, the hungry pizza in the corner, when you fall in love " +
                 "with life, you find the spark that activates life.";
        Movie soul = new Movie("Soul", 101, 8.5, 2020, 12, 25, description, review);
        soul.addCategory(animation);
        soul.addCategory(adventure);
        soul.addCategory(comedy);
        soul.addCountry(america);
        soul.addActor(foxx);
        soul.addActor(fey);
        soul.addActor(rashad);
        soul.loadPoster("soul.jpg");
        soul.setUrl("https://www.amazon.com/gp/video/detail/amzn1.dv.gti.1cbbc9e7-6fd0-b6ba-0bc7-c9e373ed4505?ref_=imdbref_tt_wbr_pvt_aiv&tag=imdbtag_tt_wbr_pvt_aiv-20");
        movieService.save(soul);

        Actor hanae = new Actor("Natsuki Hanae", 1991, 6, 26, Gender.MALE, japan);
        Actor hosoya = new Actor("Yoshimasa Hosoya", 1982, 2, 10, Gender.MALE, japan);
        Actor mimori = new Actor("Suzuko Mimori", 1986, 6, 28, Gender.FEMALE, japan);
        actorService.save(hanae);
        actorService.save(hosoya);
        actorService.save(mimori);

        description = "The team discovers that when they grow up, their relationship with their Digimon will " +
                      "come closer to an end. They realize that the more they fight, the faster their bond " +
                      "breaks and the time to choose is approaching fast.";
        review = "The best salute to childhood, and the last farewell to childhood.";
        Movie digimon = new Movie("Digimon Adventure: Last Evolution Kizuna", 94, 9.5, 2020, 2, 21, description, review);
        digimon.addCategory(adventure);
        digimon.addCategory(animation);
        digimon.addCategory(action);
        digimon.addCountry(japan);
        digimon.addActor(hanae);
        digimon.addActor(hosoya);
        digimon.addActor(mimori);
        digimon.loadPoster("digimon.jpg");
        digimon.setUrl("https://www.microsoft.com/en-us/p/digimon-adventure-last-evolution-kizuna/8d6kgwxn7hgd?activetab=pivot%3aoverviewtab");
        movieService.save(digimon);

        Actor mastroianni = new Actor("Chiara Mastroianni", 1972, 5, 28, Gender.FEMALE, france);
        Actor darrieux = new Actor("Danielle Darrieux", 1917, 5, 1, Gender.FEMALE, france);
        Actor deneuve = new Actor("Catherine Deneuve", 1943, 12, 22, Gender.FEMALE, france);
        actorService.save(mastroianni);
        actorService.save(darrieux);
        actorService.save(deneuve);

        description = "In 1970s Iran, Marjane 'Marji' Satrapi watches events through her young eyes and " +
                      "her idealistic family of a long dream being fulfilled of the hated Shah's defeat in " +
                      "the Iranian Revolution of 1979. However as Marji grows up, she witnesses first hand " +
                      "how the new Iran, now ruled by Islamic fundamentalists, has become a repressive tyranny " +
                      "on its own. With Marji dangerously refusing to remain silent at this injustice, her " +
                      "parents send her abroad to Vienna to study for a better life.";
        review = "Freedom always comes at a price.";
        Movie persepolis = new Movie("Persepolis", 96, 9.0, 2007, 5, 23, description, review);
        persepolis.addCategory(biography);
        persepolis.addCategory(animation);
        persepolis.addCategory(drama);
        persepolis.addCountry(france);
        persepolis.addCountry(america);
        persepolis.addActor(mastroianni);
        persepolis.addActor(darrieux);
        persepolis.addActor(deneuve);
        persepolis.loadPoster("persepolis.jpg");
        persepolis.setUrl("https://www.amazon.com/gp/video/detail/amzn1.dv.gti.fca9f73d-09b4-f374-9070-4b5b1a1b6545?ref_=imdbref_tt_wbr_pvt_aiv&tag=imdbtag_tt_wbr_pvt_aiv-20");
        movieService.save(persepolis);

        Actor edelstein = new Actor("Eric Edelstein", 1977, 4, 23, Gender.MALE, america);
        Actor moynihan = new Actor("Bobby Moynihan", 1978, 1, 31, Gender.MALE, america);
        Actor martin = new Actor("Demetri Martin", 1973, 5, 25, Gender.MALE, america);
        actorService.save(edelstein);
        actorService.save(moynihan);
        actorService.save(martin);

        description = "Grizz, Panda, and Ice Bear embark on an adventure to Canada after an agent from " +
                      "the Department of National Wildlife Control tries to put an end to their hi-jinx.";
        review = "Not as interesting as the former series, but Ice Bear is still so cool!";
        Movie bears = new Movie("We Bare Bears: The Movie", 69, 6.5, 2020, 6, 30, description, review);
        bears.addCategory(animation);
        bears.addCategory(adventure);
        bears.addCategory(comedy);
        bears.addCountry(america);
        bears.addActor(edelstein);
        bears.addActor(moynihan);
        bears.addActor(martin);
        bears.loadPoster("bears.jpg");
        bears.setUrl("https://www.amazon.com/We-Bare-Bears-Eric-Edelstein/dp/B089DP4HT5/ref=sr_1_1?crid=MTRTRDK471MF&dchild=1&keywords=we+bear+bears&qid=1628511708&s=instant-video&sprefix=we+bear%2Cinstant-video%2C554&sr=1-1");
        movieService.save(bears);

        Actor jung = new Actor("Yu-mi Jung", 1983, 1, 11, Gender.FEMALE, korea);
        Actor gong = new Actor("Yoo Gong", 1979, 7, 10, Gender.MALE, korea);
        Actor kim = new Actor("Mi-kyung Kim", 1963, 10, 14, Gender.FEMALE, korea);
        actorService.save(jung);
        actorService.save(gong);
        actorService.save(kim);

        description = "Kim Ji-young, an ordinary woman in her 30s, suddenly shows signs of being inhabited " +
                      "by others such as her late mother and older sister, and the stories of the people " +
                      "connected to her.";
        review = "The ordinary life of an ordinary East Asian woman. I don't want to live here anymore, " +
                 "as an East Asian woman, I will try my best to escape.";
        Movie jiyoung = new Movie("Kim Ji-young: Born 1982", 118, 9.0, 2019, 10, 23, description, review);
        jiyoung.addCategory(drama);
        jiyoung.addCountry(korea);
        jiyoung.addActor(jung);
        jiyoung.addActor(gong);
        jiyoung.addActor(kim);
        jiyoung.loadPoster("jiyoung.jpg");
        movieService.save(jiyoung);

        Actor ayase = new Actor("Haruka Ayase", 1985, 3, 24, Gender.FEMALE, japan);
        Actor nagasawa = new Actor("Masami Nagasawa", 1987, 6, 3, Gender.FEMALE, japan);
        Actor kaho = new Actor("Kaho", 1991, 6, 30, Gender.FEMALE, japan);
        actorService.save(ayase);
        actorService.save(nagasawa);
        actorService.save(kaho);

        description = "A story that revolves around three sisters who live in their grandmother's home " +
                      "and the arrival of their thirteen-year-old half sister.";
        review = "A very traditional Japanese healing movie. You will fall in love with the life in kamakura.";
        Movie sister = new Movie("Our Little Sister", 127, 8.5, 2015, 6, 13, description, review);
        sister.addCategory(family);
        sister.addCategory(drama);
        sister.addCountry(japan);
        sister.addActor(ayase);
        sister.addActor(nagasawa);
        sister.addActor(kaho);
        sister.loadPoster("diary.jpg");
        sister.setUrl("https://www.amazon.com/gp/video/detail/amzn1.dv.gti.dcac338e-fdf1-ec5c-a647-76e63a72d243?ref_=imdbref_tt_wbr_pvt_aiv&tag=imdbtag_tt_wbr_pvt_aiv-20");
        movieService.save(sister);

        description = "Based on real events, this film depicts the story of a school for the " +
                      "hearing-impaired where young deaf students were sexually assaulted by " +
                      "the faculty members over a long period of time.";
        review = "We rise up in resistance not to change the world, but to prevent the world " +
                 "from changing us.";
        Movie silenced = new Movie("Silenced", 125, 10.0, 2011, 9, 22, description, review);
        silenced.addCategory(drama);
        silenced.addCountry(korea);
        silenced.addActor(gong);
        silenced.addActor(jung);
        silenced.loadPoster("silenced.jpg");
        silenced.setUrl("http://fdrama.net/watch-online/silenced");
        movieService.save(silenced);

        Actor song = new Actor("Kang-ho Song", 1967, 1, 17, Gender.MALE, korea);
        Actor thomas = new Actor("Thomas Kretschmann", 1962, 9, 8, Gender.MALE, germany);
        actorService.save(song);
        actorService.save(thomas);

        description = "A widowed father and taxi driver who drives a German reporter from Seoul to " +
                      "Gwangju to cover the 1980 uprising, soon finds himself regretting his decision " +
                      "after being caught in the violence around him.";
        review = "This movie always reminds me of a similar event that happened in a neighboring country " +
                 "of South Korea but ended in a completely different way.";
        Movie taxiDriver = new Movie("A Taxi Driver", 137, 10.0, 2017, 8, 2, description, review);
        taxiDriver.addCategory(action);
        taxiDriver.addCategory(drama);
        taxiDriver.addCategory(history);
        taxiDriver.addCountry(korea);
        taxiDriver.addActor(song);
        taxiDriver.addActor(thomas);
        taxiDriver.loadPoster("taxi.jpg");
        taxiDriver.setUrl("https://www.amazon.com/gp/video/detail/amzn1.dv.gti.94af88b3-b245-70b0-da1b-97041a8e44b3?ref_=imdbref_tt_wbr_pvs_piv&tag=imdbtag_tt_wbr_pvs_piv-20");
        movieService.save(taxiDriver);

        Actor yeongae = new Actor("Yeong-ae Kim", 1951, 4, 21, Gender.FEMALE, korea);
        Actor oh = new Actor("Dal-su Oh", 1968, 6, 15, Gender.MALE, korea);
        actorService.save(yeongae);
        actorService.save(oh);

        description = "A snobbish tax lawyer Song Woo-Seok becomes an attorney of his old friend Jinwoo " +
                      "after seeing him being arrested by military regime. The trial becomes a turning " +
                      "point of Song's life and he decides to devote himself in democratization movement.";
        review = "According to the Constitution of the Republic of Korea, the sovereignty of the " +
                 "Republic of Korea belongs to the state, all power is generated by the citizens, " +
                 "and the state is the citizen.";
        Movie attorney = new Movie("The Attorney", 127, 10.0, 2013, 12, 18, description, review);
        attorney.addCategory(drama);
        attorney.addCountry(korea);
        attorney.addActor(song);
        attorney.addActor(yeongae);
        attorney.addActor(oh);
        attorney.loadPoster("attorney.jpg");
        attorney.setUrl("https://www.amazon.com/Attorney-Kang-ho-Song/dp/B095733Z9F/ref=sr_1_1?dchild=1&keywords=attorney&qid=1628512043&s=instant-video&sr=1-1");
        movieService.save(attorney);

        Actor jim = new Actor("Jim Carrey", 1962, 1, 17, Gender.MALE, canada);
        Actor laura = new Actor("Laura Linney", 1964, 2, 5, Gender.FEMALE, america);
        actorService.save(jim);
        actorService.save(laura);

        description = "An insurance salesman discovers his whole life is actually a reality TV show.";
        review = "Are we also living in Truman's world?";
        Movie truman = new Movie("The Truman Show", 103, 10.0, 1988, 6, 5, description, review);
        truman.addCategory(comedy);
        truman.addCategory(drama);
        truman.addCountry(america);
        truman.addActor(jim);
        truman.addActor(laura);
        truman.loadPoster("truman.jpg");
        truman.setUrl("https://play.google.com/store/movies/details/The_Truman_Show?id=LFTz6_lqWhg");
        movieService.save(truman);

        Actor minhee = new Actor("Min-hee Kim", 1982, 3, 1, Gender.FEMALE, korea);
        Actor taeri = new Actor("Tae-ri Kim", 1990, 4, 24, Gender.FEMALE, korea);
        actorService.save(minhee);
        actorService.save(taeri);

        description = "1930s Korea, in the period of Japanese occupation, a new girl (Sookee) is hired as " +
                      "a handmaiden to a Japanese heiress (Hideko) who lives a secluded life on a large " +
                      "countryside estate with her domineering Uncle (Kouzuki). But the maid has a secret. " +
                      "She is a pickpocket recruited by a swindler posing as a Japanese Count to help him " +
                      "seduce the Lady to elope with him, rob her of her fortune, and lock her up in a " +
                      "madhouse. The plan seems to proceed according to plan until Sookee and Hideko " +
                      "discover some unexpected emotions.";
        review = "A wonderful love story between girls. My favorite shot is: Sookee and Hideko carrying " +
                 "their luggage in the night, running past the huge cherry tree, breathing the free air and " +
                 "embracing the vast world.";
        Movie handmaiden = new Movie("The Handmaiden", 168, 9.5,2016, 6, 1, description, review);
        handmaiden.addCategory(drama);
        handmaiden.addCategory(thriller);
        handmaiden.addCategory(romance);
        handmaiden.addCountry(korea);
        handmaiden.addActor(minhee);
        handmaiden.addActor(taeri);
        handmaiden.loadPoster("handmaiden.jpg");
        handmaiden.setUrl("https://www.amazon.com/Handmaiden-KIM-MIN-hee/dp/B08J8LSZ4S/ref=sr_1_1?dchild=1&keywords=The+Handmaiden&qid=1628512215&s=instant-video&sr=1-1");
        movieService.save(handmaiden);

        for (Actor actor: actorService.findAll()) {
            actor.loadPhoto(actor.getName() + ".jpg");
            actorService.save(actor);
        }
        System.out.println("Load actors and movies...");
    }

}
