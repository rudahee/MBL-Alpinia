package net.rudahee.movies.shared.run;

import net.rudahee.movies.group.model.api.CreateGroupDTO;
import net.rudahee.movies.group.repository.GroupRepository;
import net.rudahee.movies.group.service.GroupService;
import net.rudahee.movies.movie.model.api.CreateMovieDTO;
import net.rudahee.movies.movie.service.MovieService;
import net.rudahee.movies.shared.exceptions.APIException;
import net.rudahee.movies.user.model.api.RegisterDTO;
import net.rudahee.movies.user.repository.UserRepository;
import net.rudahee.movies.user.services.AuthenticationService;
import net.rudahee.movies.user_movie.model.api.UserRatingMovieDTO;
import net.rudahee.movies.user_movie.service.RatingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class DatabaseInitialzr implements CommandLineRunner {

    private final MovieService movieService;
    private final AuthenticationService authService;
    private final UserRepository userRepository;
    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final RatingService ratingService;


    private static final List<String> ALL_PLATFORMS = List.of("PRIME_VIDEO", "APPLE_TV", "DISNEY_PLUS", "GOOGLE_PLAY", "HBO_MAX", "NETFLIX", "YOUTUBE");
    private static final Random RANDOM = new Random();

    public DatabaseInitialzr(MovieService movieService,
                             AuthenticationService authService,
                             UserRepository userRepository,
                             GroupService groupService,
                             GroupRepository groupRepository,
                             RatingService ratingService) {
        this.movieService = movieService;
        this.authService = authService;
        this.userRepository = userRepository;
        this.groupService = groupService;
        this.groupRepository = groupRepository;
        this.ratingService = ratingService;
    }

    @Override
    public void run(String... args) throws APIException {
        if (false) {
            populateMovies();
        }
        if (false) {
            populateUsers();
        }
        if (false) {
            populateGroups();
        }
        if (false) {
            populateUsersToGroups();
        }
        if (false) {
            populateMovieRatings();
        }
    }


    private void populateMovieRatings() throws APIException {
        // Movie id, null, User id, rating, comment

        List<UserRatingMovieDTO> ratings = Arrays.asList(
                new UserRatingMovieDTO(UUID.fromString("0433a8cf-fc4b-4c3b-8647-07e476fbc3bd"), null, UUID.fromString("2429213f-f79a-48b7-bc28-cc1427fe3f47"), 8.0f, "Excellent movie!"),
                new UserRatingMovieDTO(UUID.fromString("04db2779-03e7-4a2a-8f8a-fccc7573d270"), null, UUID.fromString("3fcafa2d-ebb4-49c9-b0ce-6adebf47d894"), 6.5f, "Enjoyed it, but it could be better."),
                new UserRatingMovieDTO(UUID.fromString("04edd3e5-528b-4c3f-bc52-9dc346e97d89"), null, UUID.fromString("98a31434-5777-4bdc-bc4e-59f63561e388"), 9.0f, "One of the best films I've seen!"),
                new UserRatingMovieDTO(UUID.fromString("09fef283-293a-42b8-ad3a-b9b3523417fc"), null, UUID.fromString("b75a6316-60e9-4275-a93f-11189048a50f"), 4.0f, "Not really my taste."),
                new UserRatingMovieDTO(UUID.fromString("0e899095-abf3-415d-8de0-08715a8e4261"), null, UUID.fromString("bccc2ff0-7b6c-404d-a9c4-ab141be30507"), 7.5f, "Great plot and characters!"),
                new UserRatingMovieDTO(UUID.fromString("0f78a2c2-bbbb-4130-b484-5afd12e4f97d"), null, UUID.fromString("e3bbf75f-6c1f-4e16-8cc2-8612d3d70763"), 5.0f, "It was okay."),
                new UserRatingMovieDTO(UUID.fromString("1c7a149e-6aac-4856-b2bc-0f032b1cc21e"), null, UUID.fromString("ecd36fae-050d-4f93-aa0e-95b2ac174bb7"), 8.5f, "I really liked it!"),
                new UserRatingMovieDTO(UUID.fromString("210dd83a-219a-46f3-95c2-4c1d0ea4772e"), null, UUID.fromString("f79c4ef7-31a3-4bf9-83bc-8c42ab272be9"), 3.0f, "Did not enjoy."),
                new UserRatingMovieDTO(UUID.fromString("22409d8d-2b1d-44ee-9867-94db64666afc"), null, UUID.fromString("fc1b2c69-9bb6-4313-b71a-6f893f8b1f45"), 10.0f, "Amazing experience!"),
                new UserRatingMovieDTO(UUID.fromString("2730d43a-bb78-4ab6-ab78-84fd4f6bd766"), null, UUID.fromString("2429213f-f79a-48b7-bc28-cc1427fe3f47"), 9.5f, "Highly recommended!"),
                new UserRatingMovieDTO(UUID.fromString("48f7ab88-c105-4e95-9728-21ff23ee757a"), null, UUID.fromString("3fcafa2d-ebb4-49c9-b0ce-6adebf47d894"), 4.5f, "Not what I expected."),
                new UserRatingMovieDTO(UUID.fromString("498b01a1-347e-40c3-81c4-a7631f9af6db"), null, UUID.fromString("98a31434-5777-4bdc-bc4e-59f63561e388"), 7.0f, "Pretty good, worth a watch."),
                new UserRatingMovieDTO(UUID.fromString("56481d50-ec66-4dc3-b9c1-60b27ddcca61"), null, UUID.fromString("b75a6316-60e9-4275-a93f-11189048a50f"), 8.0f, "Really enjoyable."),
                new UserRatingMovieDTO(UUID.fromString("5cc946cb-dfc3-4ace-a74d-a280b39569fe"), null, UUID.fromString("bccc2ff0-7b6c-404d-a9c4-ab141be30507"), 5.0f, "Just okay."),
                new UserRatingMovieDTO(UUID.fromString("5df1c055-8f5e-45d3-95a5-a4551118bf5e"), null, UUID.fromString("e3bbf75f-6c1f-4e16-8cc2-8612d3d70763"), 9.0f, "Loved it!"),
                new UserRatingMovieDTO(UUID.fromString("6041bd40-8744-47e7-b3c4-1599a00c3bc3"), null, UUID.fromString("ecd36fae-050d-4f93-aa0e-95b2ac174bb7"), 6.0f, "It was fine."),
                new UserRatingMovieDTO(UUID.fromString("609fb775-a92a-4a7b-bf7b-aea4be2fd10c"), null, UUID.fromString("f79c4ef7-31a3-4bf9-83bc-8c42ab272be9"), 8.3f, "Great watch!"),
                new UserRatingMovieDTO(UUID.fromString("7967b4e6-fda3-44c8-91f1-372e64b1e367"), null, UUID.fromString("fc1b2c69-9bb6-4313-b71a-6f893f8b1f45"), 10.0f, "A must-see!"),
                new UserRatingMovieDTO(UUID.fromString("79ef1701-661a-4b67-b352-5c9cdf3f57c8"), null, UUID.fromString("2429213f-f79a-48b7-bc28-cc1427fe3f47"), 8.0f, "Very good movie."),
                new UserRatingMovieDTO(UUID.fromString("7bdf22fc-8a09-4957-b531-819a8e62afff"), null, UUID.fromString("3fcafa2d-ebb4-49c9-b0ce-6adebf47d894"), 3.2f, "Not my favorite."),
                new UserRatingMovieDTO(UUID.fromString("7fae42ee-b0d8-428f-9e84-9231e14b342a"), null, UUID.fromString("98a31434-5777-4bdc-bc4e-59f63561e388"), 7.0f, "Good but had some flaws."),
                new UserRatingMovieDTO(UUID.fromString("8070403f-ed76-405b-a763-2eee765d66d0"), null, UUID.fromString("b75a6316-60e9-4275-a93f-11189048a50f"), 9.8f, "Fantastic storyline!"),
                new UserRatingMovieDTO(UUID.fromString("9dfa5e9d-894f-402a-9248-6be4713456ec"), null, UUID.fromString("bccc2ff0-7b6c-404d-a9c4-ab141be30507"), 2.0f, "Meh, not great."),
                new UserRatingMovieDTO(UUID.fromString("a712a09c-078f-4aaf-a1c4-c626209e41d5"), null, UUID.fromString("e3bbf75f-6c1f-4e16-8cc2-8612d3d70763"), 8.7f, "Absolutely loved it!"),
                new UserRatingMovieDTO(UUID.fromString("aaa9449f-a209-4b5c-b4e1-66a8a5d9b5fa"), null, UUID.fromString("ecd36fae-050d-4f93-aa0e-95b2ac174bb7"), 5.4f, "Just okay."),
                new UserRatingMovieDTO(UUID.fromString("b10107ca-5f9c-4d4d-b85d-9125d6b6e271"), null, UUID.fromString("f79c4ef7-31a3-4bf9-83bc-8c42ab272be9"), 10.0f, "Best movie ever!"),
                new UserRatingMovieDTO(UUID.fromString("bdb0d093-3d5c-4142-b46a-47dafe28539e"), null, UUID.fromString("fc1b2c69-9bb6-4313-b71a-6f893f8b1f45"), 1.5f, "Not good at all."),
                new UserRatingMovieDTO(UUID.fromString("cdcea418-5b0d-4ce7-9eb4-767d8fcc05f0"), null, UUID.fromString("2429213f-f79a-48b7-bc28-cc1427fe3f47"), 4.5f, "I enjoyed this one."),
                new UserRatingMovieDTO(UUID.fromString("d3328193-149a-4038-9d47-76553ad57b2e"), null, UUID.fromString("3fcafa2d-ebb4-49c9-b0ce-6adebf47d894"), 3.0f, "It was okay."),
                new UserRatingMovieDTO(UUID.fromString("e33b4925-c29b-4292-9a5f-a9f04f0deea9"), null, UUID.fromString("98a31434-5777-4bdc-bc4e-59f63561e388"), 4.6f, "Really good film!"),
                new UserRatingMovieDTO(UUID.fromString("e741e415-9fa6-46f1-a7ac-5267d5eda05f"), null, UUID.fromString("b75a6316-60e9-4275-a93f-11189048a50f"), 2.3f, "It was average."),
                new UserRatingMovieDTO(UUID.fromString("fe2cbcb6-b55b-4255-9cec-f6db0dfdd2a6"), null, UUID.fromString("bccc2ff0-7b6c-404d-a9c4-ab141be30507"), 3.7f, "Not bad."),
                new UserRatingMovieDTO(UUID.fromString("ff753d1b-b960-4a7d-8017-441daba0e492"), null, UUID.fromString("e3bbf75f-6c1f-4e16-8cc2-8612d3d70763"), 4.0f, "Nice movie!"),
                new UserRatingMovieDTO(UUID.fromString("0433a8cf-fc4b-4c3b-8647-07e476fbc3bd"), null, UUID.fromString("f79c4ef7-31a3-4bf9-83bc-8c42ab272be9"), 6.8f, "Good but not great."),
                new UserRatingMovieDTO(UUID.fromString("04db2779-03e7-4a2a-8f8a-fccc7573d270"), null, UUID.fromString("fc1b2c69-9bb6-4313-b71a-6f893f8b1f45"), 5.5f, "It was okay."),
                new UserRatingMovieDTO(UUID.fromString("04edd3e5-528b-4c3f-bc52-9dc346e97d89"), null, UUID.fromString("98a31434-5777-4bdc-bc4e-59f63561e388"), 9.3f, "Loved the cinematography!"),
                new UserRatingMovieDTO(UUID.fromString("09fef283-293a-42b8-ad3a-b9b3523417fc"), null, UUID.fromString("b75a6316-60e9-4275-a93f-11189048a50f"), 2.0f, "Did not enjoy at all."),
                new UserRatingMovieDTO(UUID.fromString("0e899095-abf3-415d-8de0-08715a8e4261"), null, UUID.fromString("bccc2ff0-7b6c-404d-a9c4-ab141be30507"), 7.5f, "A solid film with a good plot."),
                new UserRatingMovieDTO(UUID.fromString("0f78a2c2-bbbb-4130-b484-5afd12e4f97d"), null, UUID.fromString("e3bbf75f-6c1f-4e16-8cc2-8612d3d70763"), 8.0f, "Really enjoyed it!"),
                new UserRatingMovieDTO(UUID.fromString("1c7a149e-6aac-4856-b2bc-0f032b1cc21e"), null, UUID.fromString("ecd36fae-050d-4f93-aa0e-95b2ac174bb7"), 6.3f, "Good performances but weak story."),
                new UserRatingMovieDTO(UUID.fromString("210dd83a-219a-46f3-95c2-4c1d0ea4772e"), null, UUID.fromString("f79c4ef7-31a3-4bf9-83bc-8c42ab272be9"), 9.5f, "A masterpiece!"),
                new UserRatingMovieDTO(UUID.fromString("22409d8d-2b1d-44ee-9867-94db64666afc"), null, UUID.fromString("2429213f-f79a-48b7-bc28-cc1427fe3f47"), 4.0f, "Had some interesting moments."),
                new UserRatingMovieDTO(UUID.fromString("2730d43a-bb78-4ab6-ab78-84fd4f6bd766"), null, UUID.fromString("3fcafa2d-ebb4-49c9-b0ce-6adebf47d894"), 7.2f, "Quite enjoyable, but a bit predictable."),
                new UserRatingMovieDTO(UUID.fromString("0433a8cf-fc4b-4c3b-8647-07e476fbc3bd"), null, UUID.fromString("2429213f-f79a-48b7-bc28-cc1427fe3f47"), 8.1f, "Fantastic storyline!"),
                new UserRatingMovieDTO(UUID.fromString("04db2779-03e7-4a2a-8f8a-fccc7573d270"), null, UUID.fromString("3fcafa2d-ebb4-49c9-b0ce-6adebf47d894"), 6.7f, "Good, but could be better."),
                new UserRatingMovieDTO(UUID.fromString("04edd3e5-528b-4c3f-bc52-9dc346e97d89"), null, UUID.fromString("98a31434-5777-4bdc-bc4e-59f63561e388"), 9.0f, "Absolutely loved it!"),
                new UserRatingMovieDTO(UUID.fromString("09fef283-293a-42b8-ad3a-b9b3523417fc"), null, UUID.fromString("b75a6316-60e9-4275-a93f-11189048a50f"), 3.8f, "Not my cup of tea."),
                new UserRatingMovieDTO(UUID.fromString("0e899095-abf3-415d-8de0-08715a8e4261"), null, UUID.fromString("bccc2ff0-7b6c-404d-a9c4-ab141be30507"), 7.5f, "Pretty good overall."),
                new UserRatingMovieDTO(UUID.fromString("0f78a2c2-bbbb-4130-b484-5afd12e4f97d"), null, UUID.fromString("e3bbf75f-6c1f-4e16-8cc2-8612d3d70763"), 8.9f, "An enjoyable experience."),
                new UserRatingMovieDTO(UUID.fromString("1c7a149e-6aac-4856-b2bc-0f032b1cc21e"), null, UUID.fromString("ecd36fae-050d-4f93-aa0e-95b2ac174bb7"), 4.5f, "Average at best."),
                new UserRatingMovieDTO(UUID.fromString("210dd83a-219a-46f3-95c2-4c1d0ea4772e"), null, UUID.fromString("f79c4ef7-31a3-4bf9-83bc-8c42ab272be9"), 9.8f, "A true masterpiece!"),
                new UserRatingMovieDTO(UUID.fromString("22409d8d-2b1d-44ee-9867-94db64666afc"), null, UUID.fromString("2429213f-f79a-48b7-bc28-cc1427fe3f47"), 5.5f, "Decent, but forgettable."),
                new UserRatingMovieDTO(UUID.fromString("2730d43a-bb78-4ab6-ab78-84fd4f6bd766"), null, UUID.fromString("3fcafa2d-ebb4-49c9-b0ce-6adebf47d894"), 7.2f, "Enjoyable but had flaws.")
        );

        for (UserRatingMovieDTO rating : ratings) {
            ratingService.userRatingMovie(rating);
        }
    }

    private void populateGroups() throws APIException {
        List<CreateGroupDTO> groupsToCreate = Arrays.asList(
            new CreateGroupDTO("Study Group", UUID.fromString("2429213f-f79a-48b7-bc28-cc1427fe3f47")), // Creador
            new CreateGroupDTO("Sports Club", UUID.fromString("3fcafa2d-ebb4-49c9-b0ce-6adebf47d894")),
            new CreateGroupDTO("Book Club", UUID.fromString("8e2bbd96-1327-4200-8f3e-c5180959449e"))
        );

        for (CreateGroupDTO groupDTO : groupsToCreate) {
            groupService.createGroup(groupDTO);
        }
    }

    private void populateUsersToGroups() throws APIException {
        groupService.joinMemberByUserId("5d7c", UUID.fromString("98a31434-5777-4bdc-bc4e-59f63561e388"));
        groupService.joinMemberByUserId("5d7c", UUID.fromString("b75a6316-60e9-4275-a93f-11189048a50f"));
        groupService.joinMemberByUserId("5d7c", UUID.fromString("bccc2ff0-7b6c-404d-a9c4-ab141be30507"));
        groupService.joinMemberByUserId("5d7c", UUID.fromString("e3bbf75f-6c1f-4e16-8cc2-8612d3d70763"));
        groupService.joinMemberByUserId("5d7c", UUID.fromString("ecd36fae-050d-4f93-aa0e-95b2ac174bb7"));
        groupService.joinMemberByUserId("5d7c", UUID.fromString("f79c4ef7-31a3-4bf9-83bc-8c42ab272be9"));
        groupService.joinMemberByUserId("5d7c", UUID.fromString("fc1b2c69-9bb6-4313-b71a-6f893f8b1f45"));
        groupService.joinMemberByUserId("5d7c", UUID.fromString("2429213f-f79a-48b7-bc28-cc1427fe3f47"));
        groupService.joinMemberByUserId("7a67", UUID.fromString("f79c4ef7-31a3-4bf9-83bc-8c42ab272be9"));
        groupService.joinMemberByUserId("7a67", UUID.fromString("fc1b2c69-9bb6-4313-b71a-6f893f8b1f45"));
        groupService.joinMemberByUserId("7a67", UUID.fromString("2429213f-f79a-48b7-bc28-cc1427fe3f47"));
    }

    private void populateUsers() {
        List<RegisterDTO> users = Arrays.asList(
                new RegisterDTO("JohnDoe", "P@ssw0rd1", "John Doe", "johndoe@example.com"),
                new RegisterDTO("JaneDoe", "P@ssw0rd2", "Jane Doe", "janedoe@example.com"),
                new RegisterDTO("Michael.Smith", "P@ssw0rd3", "Michael Smith", "michael.smith@example.com"),
                new RegisterDTO("3mily.1ohnson", "P@ssw0rd4", "Emily Johnson", "emily.johnson@example.com"),
                new RegisterDTO("William.Br0wn", "P@ssw0rd5", "William Brown", "william.brown@example.com"),
                new RegisterDTO("Sophia.Williams", "P@ssw0rd6", "Sophia Williams", "sophia.williams@example.com"),
                new RegisterDTO("James Jones", "P@ssw0rd7", "James Jones", "james.jones@example.com"),
                new RegisterDTO("Olivia Miller", "P@ssw0rd8", "Olivia Miller", "olivia.miller@example.com"),
                new RegisterDTO("Benjamin Davis", "P@ssw0rd9", "Benjamin Davis", "benjamin.davis@example.com"),
                new RegisterDTO("Ava Garcia", "P@ssw0rd10", "Ava Garcia", "ava.garcia@example.com")
        );

        for (RegisterDTO userDTO : users) {
            authService.signup(userDTO);
        }
    }

    private void populateMovies() {
        List<CreateMovieDTO> movies = Arrays.asList(
                new CreateMovieDTO("The Great Gatsby", "A man pursues his former lover in the 1920s.", "Set in the Roaring Twenties, The Great Gatsby tells the story of Jay Gatsby, a mysterious millionaire who throws extravagant parties at his Long Island mansion in hopes of reuniting with his lost love, Daisy Buchanan. Through the eyes of Nick Carraway, Daisy's cousin and the novel's narrator, the story explores themes of love, desire, wealth, and the American Dream, while unraveling the dark secrets surrounding Gatsby and his obsession with the past.", "https://upload.wikimedia.org/wikipedia/en/f/fc/Great_gatsby_74.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Cabinet of Dr. Caligari", "A mad doctor uses somnambulism to commit murder.", "The Cabinet of Dr. Caligari is a landmark silent horror film that follows Francis, a young man who recounts the disturbing events surrounding a traveling sideshow in a small town. The enigmatic Dr. Caligari, a sinister figure, showcases a sleepwalking man named Cesare, who can predict people's futures. As a series of mysterious murders occur, Francis becomes increasingly suspicious of Caligari's involvement. The film is notable for its striking visual style, featuring distorted sets and sharp angles, which enhances its nightmarish atmosphere and themes of madness and authority", "https://upload.wikimedia.org/wikipedia/en/thumb/2/2f/The_Cabinet_of_Dr._Caligari_poster.jpg/445px-The_Cabinet_of_Dr._Caligari_poster.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("M", "A serial killer preys on children in Berlin.", "M is a gripping German expressionist thriller that tells the story of a serial killer who preys on children in Berlin. The film follows the city as it descends into chaos, with citizens demanding justice and the police launching a frantic manhunt. As the murderer, played by Peter Lorre, evades capture, the criminal underworld also becomes involved, leading to a tense and dramatic confrontation. M explores themes of morality, societal fear, and the psychological turmoil of both the hunter and the hunted, making it a pioneering work in the crime and psychological thriller genres.", "https://upload.wikimedia.org/wikipedia/en/a/ab/M_poster.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Battle of the Somme", "Documentary about World War I.", "The Battle of the Somme is a landmark British documentary film that chronicles one of the largest battles of World War I, fought between July and November 1916. The film presents a harrowing depiction of the war's realities, combining actual footage of the battle with staged reenactments. It highlights the experiences of soldiers on the front lines, showcasing both the heroism and the tragic losses encountered during the conflict. Released in 1916, it aimed to provide a stark visual record of the war's devastation, ultimately becoming one of the most influential war documentaries in cinematic history.", "https://upload.wikimedia.org/wikipedia/commons/5/52/Somme-film-ad.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("Nosferatu", "A vampire terrorizes a town.", "Nosferatu is a silent horror film directed by F.W. Murnau, released in 1922. It is an unauthorized adaptation of Bram Stoker's Dracula and tells the story of Count Orlok, a sinister vampire who brings terror to the small town of Wisborg. The film follows Thomas Hutter, a real estate agent, who travels to Transylvania to facilitate Orlok's move to the city. As Orlok's dark presence spreads, the townspeople fall prey to his supernatural powers. Renowned for its eerie atmosphere, innovative cinematography, and iconic imagery, Nosferatu is considered a masterpiece of the horror genre and a seminal work in the history of cinema.", "https://cdn.posteritati.com/posters/000/000/067/494/nosferatu-md-web.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("Metropolis", "Futuristic city divided by class.", "Metropolis is a 1927 silent science fiction film directed by Fritz Lang. Set in a dystopian future, it presents a stark division between the wealthy elite who live in luxury above ground and the oppressed workers who toil in harsh conditions below. The story follows Freder, the son of the city's mastermind, who discovers the grim reality of the workers' lives and falls in love with Maria, a woman who inspires hope for a better future. When Freder learns of a plot to incite violence among the workers, he seeks to bridge the gap between the classes. The film is renowned for its groundbreaking special effects, expressionist visuals, and themes of social conflict and industrialization, making it a landmark in cinematic history.", "https://pics.filmaffinity.com/metropolis-434117468-large.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The General", "A train engineer pursues his stolen locomotive.", "The General is a 1926 silent comedy film directed by Buster Keaton and Clyde Bruckman. Set during the American Civil War, the film follows Johnnie Gray, a proud locomotive engineer whose beloved train, the General, is stolen by Union spies. Determined to win back both his train and the affections of his girlfriend, Annabelle Lee, Johnnie embarks on a daring and comedic chase. The film features elaborate stunts, including breathtaking train sequences and Keaton's signature deadpan humor. \"The General\" is celebrated for its inventive physical comedy and is often regarded as one of the greatest silent films ever made.", "https://pics.filmaffinity.com/the_general-145423563-mmed.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Kid", "A tramp helps an abandoned child.", "The Kid is a 1921 silent comedy-drama film written and directed by Charlie Chaplin. The film follows the story of a Tramp who discovers an abandoned child and decides to raise him as his own. As the duo forms a close bond, they face various hardships and comedic misadventures in a harsh urban landscape. The Tramp's attempts to provide for the Kid lead to touching moments and slapstick humor. Ultimately, the film explores themes of love, family, and resilience, culminating in a poignant conclusion that showcases Chaplin's ability to blend humor with deep emotional resonance. The Kid remains one of Chaplin's most beloved works and a classic of early cinema.", "https://upload.wikimedia.org/wikipedia/commons/5/59/CC_The_Kid_1921.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Gold Rush", "A prospector in the Yukon goes through hardships.", "The Gold Rush is a 1925 silent comedy film written, directed by, and starring Charlie Chaplin. The film follows the story of the Tramp as he embarks on a journey to the Yukon during the Klondike Gold Rush. Facing harsh conditions and the challenges of survival, he encounters a variety of quirky characters, including a beautiful dance hall girl and a tough prospector. The film is famous for its iconic scenes, including the Tramp's comedic struggles with food and his desperate attempts to woo the girl of his dreams. Through a mix of humor and pathos, The Gold Rush explores themes of perseverance, love, and the human spirit's resilience in the face of adversity. It is regarded as one of Chaplin's finest works and a landmark in cinematic history.", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Gold_rush_poster.jpg/220px-Gold_rush_poster.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Phantom of the Opera", "A disfigured musical genius haunts a Paris opera house.", "The Phantom of the Opera is a 1925 silent horror film directed by Rupert Julian and based on Gaston Leroux's novel. The story is set in the opulent Paris Opera House, where a mysterious figure known as the Phantom haunts the corridors and becomes infatuated with a beautiful soprano named Christine Daaé. As Christine rises to fame, the Phantom's obsession drives him to manipulate events to keep her close, leading to dramatic confrontations and tragic consequences. The film is renowned for its elaborate sets, makeup, and the chilling portrayal of the Phantom, played by Lon Chaney. With themes of love, obsession, and the duality of beauty and monstrosity, The Phantom of the Opera remains a classic of silent cinema and a haunting exploration of the human psyche.", "https://upload.wikimedia.org/wikipedia/en/thumb/e/ef/The_Phantom_of_the_Opera_%281986_musical%29.jpg/220px-The_Phantom_of_the_Opera_%281986_musical%29.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Birth of a Nation", "A controversial epic of the Civil War.", "The Birth of a Nation is a 1915 silent film directed by D.W. Griffith, notable for its groundbreaking cinematic techniques but also controversial for its portrayal of African Americans and its glorification of the Ku Klux Klan. The film is set during the American Civil War and Reconstruction era, following the lives of two families: the Camerons from the South and the Stonemans from the North. It depicts the impact of the war on their lives and the subsequent chaos of Reconstruction, framed through a romanticized and racially charged narrative. While it was revolutionary for its innovations in film, including the use of close-ups and cross-cutting, its racial themes have sparked significant debate and criticism, making it a complex and contentious piece of film history. The Birth of a Nation remains a landmark in cinema, both for its technical achievements and its troubling legacy.", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/Birth_of_a_Nation_theatrical_poster.jpg/220px-Birth_of_a_Nation_theatrical_poster.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("Sherlock Jr.", "A film projectionist dreams of being a detective.", "Sherlock Jr. is a 1924 silent comedy film directed by and starring Buster Keaton. The story follows a film projectionist who dreams of becoming a detective. When his girlfriend is falsely accused of theft, he embarks on a whimsical adventure to clear her name. The film is renowned for its inventive visual gags and innovative special effects, including a famous scene where Keaton steps into the movie screen itself, leading to a series of surreal escapades. Blending physical comedy with a clever narrative, Sherlock Jr. showcases Keaton's extraordinary talent for visual storytelling and remains a beloved classic in the realm of silent cinema. The film explores themes of imagination, illusion, and the quest for justice, all while delivering Keaton's signature blend of humor and poignancy.", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Sherlock_jr_poster.jpg/220px-Sherlock_jr_poster.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Last Man on Earth", "A scientist fights to survive in a post-apocalyptic world.", "The Last Man on Earth is a 1964 science fiction horror film directed by Ubaldo Ragona and Sidney Salkow, starring Vincent Price. The story is set in a post-apocalyptic world where a plague has wiped out humanity, leaving Price's character, Robert Morgan, as the sole survivor. He spends his days hunting vampires—those infected by the virus—and fortifying his home against nighttime attacks. As he grapples with loneliness and despair, he reflects on his past life and the loss of his loved ones. The film explores themes of isolation, survival, and the human condition, culminating in a tense and thought-provoking conclusion that raises questions about humanity and morality. With its atmospheric tension and Price's compelling performance, The Last Man on Earth remains a notable entry in the genre of apocalyptic cinema.", "https://upload.wikimedia.org/wikipedia/en/thumb/7/73/Lastmanonearth1960s.jpg/220px-Lastmanonearth1960s.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Mark of Zorro", "A masked hero fights for justice in California.", "The Mark of Zorro is a 1920 silent adventure film directed by Fred Niblo and starring Douglas Fairbanks. The film follows the story of Don Diego Vega, a nobleman who adopts the alter ego of Zorro, a masked vigilante who fights against oppression in colonial California. Using his swordsmanship and cunning, Zorro defends the downtrodden while outsmarting the corrupt officials and soldiers who terrorize the local populace. The film is notable for its swashbuckling action sequences, romance, and humor, showcasing Fairbanks' charisma and athleticism. As Zorro, he leaves his signature mark—a \"Z\" carved into his foes' clothing—symbolizing hope and resistance against tyranny. The Mark of Zorro helped establish the iconic character and set the standard for future adaptations, becoming a classic in the adventure genre.", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d2/The_Mark_of_Zorro_%281940_film_poster%29.jpg/220px-The_Mark_of_Zorro_%281940_film_poster%29.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Adventures of Sherlock Holmes", "Holmes and Watson tackle their most challenging cases.", "The Adventures of Sherlock Holmes is a 1985 television miniseries based on Arthur Conan Doyle's collection of short stories featuring the famous detective Sherlock Holmes and his loyal companion, Dr. John Watson. Set in Victorian London, the series follows Holmes as he employs his extraordinary powers of deduction to solve various intricate mysteries. Each episode showcases a different case, highlighting Holmes's intellect, keen observation skills, and unique methods of investigation. From uncovering hidden motives to unraveling complex plots, Holmes tackles everything from thefts to murders, all while engaging in witty banter with Watson. The series is notable for its faithful adaptation of Doyle's original stories and its portrayal of Holmes as both a brilliant detective and a deeply flawed individual, showcasing his struggles with isolation and addiction. The Adventures of Sherlock Holmes remains a beloved representation of the iconic character and his adventures.", "https://m.media-amazon.com/images/I/514n2XC3hzL._AC_UF894,1000_QL80_.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("Hunchback of Notre Dame", "A deformed bell-ringer loves a beautiful gypsy.", "The Hunchback of Notre Dame is a 1923 silent film directed by Wallace Worsley, based on Victor Hugo's classic novel. The story unfolds in 15th-century Paris, centering on Quasimodo, the deformed bell-ringer of the Notre Dame Cathedral, who is shunned by society. Despite his physical appearance, Quasimodo possesses a kind heart and a deep affection for the beautiful gypsy, Esmeralda, who shows him kindness. As the narrative progresses, themes of love, acceptance, and social injustice emerge, culminating in a tragic love story marked by Quasimodo's unrequited feelings and the violent clashes of fate. The film is renowned for its dramatic performances, particularly Lon Chaney's portrayal of Quasimodo, and its stunning visual representation of medieval Paris. The Hunchback of Notre Dame remains a poignant exploration of humanity, compassion, and the struggle against societal prejudice.", "https://upload.wikimedia.org/wikipedia/en/2/26/The_Hunchback_of_Notre_Dame_1996_poster.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("A Night at the Opera", "A comedic tale of love and rivalry.", "A Night at the Opera is a 1935 comedy film starring the Marx Brothers, directed by Sam Wood. The film centers on the brothers' antics as they attempt to help a young couple, Otis B. Driftwood and Rosa, navigate the world of opera and the complexities of love. Their schemes involve sabotaging a rival opera company and creating chaos at a prestigious performance. Filled with the signature humor and clever wordplay of the Marx Brothers, the film showcases memorable musical numbers and hilarious slapstick routines. The comedic climax unfolds during a chaotic opera performance, leading to a series of misunderstandings and humorous confrontations. A Night at the Opera is celebrated for its blend of highbrow and lowbrow comedy, making it one of the classic films of the genre and a beloved favorite among fans of the Marx Brothers.", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/A_Night_at_the_Opera_film_poster.jpg/220px-A_Night_at_the_Opera_film_poster.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Battleship Potemkin", "A dramatized version of a naval mutiny.", "The Battleship Potemkin is a 1925 silent film directed by Sergei Eisenstein, renowned for its groundbreaking techniques in montage and its powerful political message. The film depicts a mutiny aboard the Russian battleship Potemkin during the 1905 Revolution, focusing on the crew's revolt against their oppressive officers. Through striking imagery and dynamic editing, Eisenstein portrays the harrowing experiences of the sailors, including the infamous Odessa Steps sequence, where a massacre unfolds as citizens support the mutineers. The film serves as a potent symbol of revolutionary fervor and collective struggle, reflecting the socio-political climate of its time. Its innovative visual style and dramatic storytelling have solidified The Battleship Potemkin as a landmark in cinematic history and a classic of world cinema.", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Vintage_Potemkin.jpg/220px-Vintage_Potemkin.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("Safety Last!", "A man tries to impress his girlfriend in the city.", "Safety Last! is a 1923 silent comedy film directed by Fred C. Newmeyer and Sam Taylor, featuring the iconic silent film star Harold Lloyd. The film follows a young man who moves to the city to make a name for himself and impress his girlfriend. In a bid to attract attention and boost sales for his employer's department store, he stages a daring stunt by climbing a skyscraper. The film is renowned for its thrilling sequences, particularly the climax where Lloyd precariously hangs from the hands of a clock high above the streets. Blending slapstick humor with suspense, Safety Last! showcases Lloyd's remarkable physical comedy and timing, making it a beloved classic that remains influential in the realm of comedic cinema. Its mix of charm, ingenuity, and heart continues to resonate with audiences today.", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Safety_last_poster.jpg/220px-Safety_last_poster.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Lodger", "A story of a man suspected to be a killer.", "The Lodger is a 1927 silent thriller directed by Alfred Hitchcock, based on Marie Belloc Lowndes' novel. The film tells the story of a mysterious lodger who arrives at a London boarding house during a series of murders targeting young women. The landlady's daughter, Daisy, becomes intrigued by the lodger, who is both charming and enigmatic. As Daisy falls for him, suspicion grows about his true identity and possible involvement in the crimes. The film is notable for its atmospheric tension, pioneering use of lighting and shadow, and themes of obsession and danger. Hitchcock's masterful direction and the film's suspenseful narrative have earned it a place as a significant work in early cinema and a precursor to many of his later films. The Lodger is often regarded as one of Hitchcock's first true masterpieces, showcasing his emerging style and storytelling prowess.", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/The_Lodger_%281927_film_poster%29.jpg/220px-The_Lodger_%281927_film_poster%29.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Strange Case of Dr. Jekyll and Mr. Hyde", "A doctor transforms into his evil alter ego.", "The Strange Case of Dr. Jekyll and Mr. Hyde is a 1920 silent horror film directed by John Barrymore, based on Robert Louis Stevenson’s classic novella. The story revolves around Dr. Henry Jekyll, a respected scientist who experiments with the duality of human nature. Obsessed with separating his good and evil selves, he creates a potion that transforms him into Edward Hyde, a monstrous alter ego who embodies his darkest impulses. As Hyde's violent behavior escalates, Jekyll struggles to maintain control over his dual identity, leading to tragic consequences. The film explores themes of morality, identity, and the struggle between good and evil. Barrymore's performance is particularly notable for its ability to capture the contrasts between the genteel Dr. Jekyll and the sinister Mr. Hyde, making the film a haunting exploration of human nature. Its influence has persisted through adaptations in various media, solidifying its place in the horror genre.", "https://upload.wikimedia.org/wikipedia/en/thumb/9/9f/The_Strange_Case_of_Dr._Jekyll_and_Mr._Hyde_1968.jpg/220px-The_Strange_Case_of_Dr._Jekyll_and_Mr._Hyde_1968.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("Dracula", "A vampire seeks to seduce a young woman.", "Dracula is a 1931 horror film directed by Tod Browning, based on Bram Stoker's iconic novel. The film introduces Count Dracula, a centuries-old vampire who travels from Transylvania to England in search of new blood. The story follows Jonathan Harker, a young solicitor who unwittingly becomes Dracula's prisoner, and his fiancée Mina, who falls under the vampire's spell. With the help of Dr. Van Helsing, a wise and determined professor, Harker and his friends attempt to confront Dracula and save Mina from his clutches. The film is renowned for its atmospheric visuals, haunting performances, particularly by Bela Lugosi as Dracula, and its pivotal role in shaping the vampire genre in cinema. It explores themes of seduction, fear, and the clash between modernity and ancient evil, establishing a lasting legacy in horror film history.", "https://upload.wikimedia.org/wikipedia/en/thumb/a/a0/Bram_Stoker%27s_Dracula_%281992_film%29.jpg/220px-Bram_Stoker%27s_Dracula_%281992_film%29.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Wizard of Oz", "A girl is transported to a magical land.", "The Wizard of Oz is a 1939 musical fantasy film directed by Victor Fleming, based on L. Frank Baum's beloved children's novel. The story follows Dorothy Gale, a young girl from Kansas who is swept away to the magical land of Oz by a tornado. There, she embarks on an adventure to find the Wizard of Oz, who she believes can help her return home. Along the way, she befriends a Scarecrow seeking a brain, a Tin Man longing for a heart, and a Cowardly Lion in search of courage. Together, they face various challenges and the wicked Wicked Witch of the West. The film is celebrated for its colorful cinematography, memorable songs like Somewhere Over the Rainbow, and its exploration of friendship, courage, and the idea that home is where the heart is. It remains a classic in American cinema and a cherished tale for audiences of all ages.", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/Wizard_of_oz_movie_poster.jpg/220px-Wizard_of_oz_movie_poster.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Three Musketeers", "A young man becomes a musketeer.", "The Three Musketeers is a 1973 swashbuckler film directed by Richard Lester, based on Alexandre Dumas's classic novel. The story follows the young and spirited d'Artagnan as he leaves his home to join the Musketeers of the Guard in 17th-century France. He quickly befriends the three elite musketeers—Athos, Porthos, and Aramis—who become his loyal companions. Together, they embark on thrilling adventures, battling the schemes of the scheming Cardinal Richelieu and his agent, the villainous Milady de Winter. The film is known for its witty dialogue, action-packed sword fights, and a sense of camaraderie among the characters. It explores themes of friendship, honor, and bravery, solidifying its place as a beloved adaptation of Dumas's timeless tale.", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/The_Three_Musketeers_%281921%29_2.jpg/220px-The_Three_Musketeers_%281921%29_2.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Count of Monte Cristo", "A tale of revenge and redemption.", "The Count of Monte Cristo is a 2002 film directed by Kevin Reynolds, adapted from Alexandre Dumas's classic novel. The story follows Edmond Dantès, a young sailor who is falsely imprisoned by his envious rival, Fernand Mondego. After years of suffering in the dark dungeon of Château d'If, Dantès learns of a hidden treasure on the Isle of Monte Cristo from a fellow prisoner. Upon escaping, he retrieves the treasure and transforms himself into the wealthy and enigmatic Count of Monte Cristo. Driven by a desire for revenge against those who betrayed him, Dantès meticulously plans his retribution while also seeking redemption. The film explores themes of vengeance, justice, and the complexities of human morality, showcasing a gripping journey of transformation and betrayal that captivates audiences.", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Louis_Fran%C3%A7ais-Dant%C3%A8s_sur_son_rocher.jpg/220px-Louis_Fran%C3%A7ais-Dant%C3%A8s_sur_son_rocher.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("20,000 Leagues Under the Sea", "An adventure under the sea.", "20,000 Leagues Under the Sea is a 1954 film directed by Richard Fleischer, based on Jules Verne's classic novel. The story follows Professor Aronnax, his servant Conseil, and a Canadian whaler named Ned Land as they embark on a daring adventure aboard the Nautilus, a submarine captained by the mysterious Captain Nemo. After being captured by the submarine, they explore the depths of the ocean, encountering breathtaking marine life and thrilling underwater battles. As the crew navigates the wonders and dangers of the sea, they grapple with themes of freedom, revenge, and the consequences of man's obsession with nature. The film is notable for its stunning special effects and innovative underwater cinematography, making it a landmark in the science fiction genre.", "https://upload.wikimedia.org/wikipedia/en/thumb/b/b8/20000leaguesposter.jpg/220px-20000leaguesposter.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("Around the World in 80 Days", "A man races around the world.", "Around the World in 80 Days is a 1956 adventure film directed by Michael Anderson, based on the classic novel by Jules Verne. The story follows Phileas Fogg, a meticulous English gentleman who makes a wager that he can circumnavigate the globe in just 80 days. Accompanied by his loyal servant Passepartout, Fogg embarks on a thrilling journey that takes them through various countries and cultures. They encounter numerous challenges, including natural disasters, thieves, and even a persistent detective who suspects Fogg of theft. Throughout their adventures, the duo experiences the beauty and diversity of the world, highlighting themes of friendship, perseverance, and the spirit of adventure. The film is renowned for its grand sets, vibrant visuals, and a memorable score, and it remains a beloved adaptation of Verne's timeless tale.", "https://upload.wikimedia.org/wikipedia/en/thumb/5/5e/Around_the_World_in_80_Days_%281956_film%29_poster.jpg/220px-Around_the_World_in_80_Days_%281956_film%29_poster.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Time Machine", "A scientist travels to the future.", "The Time Machine is a 1960 science fiction film directed by George Pal, based on the novel by H.G. Wells. The story revolves around George, an ambitious inventor who creates a time machine to explore the future. Driven by his desire to understand the fate of humanity, he travels far into the distant future, where he encounters two distinct species: the peaceful Eloi and the monstrous Morlocks. As George navigates this new world, he grapples with themes of evolution, class struggle, and the consequences of technological advancement. The film features innovative special effects for its time and a memorable score, capturing the imagination of audiences and cementing its place as a classic in the science fiction genre.", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Two_complete_science_adventure_books_1951win_n4.jpg/220px-Two_complete_science_adventure_books_1951win_n4.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The War of the Worlds", "Earth faces an invasion from Mars.", "The War of the Worlds is a 1953 science fiction film directed by Byron Haskin, based on H.G. Wells' classic novel. The story follows a small town in California that is suddenly invaded by Martians, who arrive in massive, destructive spacecraft. As the aliens unleash deadly heat rays and toxic gas, panic spreads among the townspeople. The film explores themes of humanity's vulnerability in the face of overwhelming technological might and the fragility of civilization. As scientists and military forces scramble to find a solution, a love story develops between a young couple caught in the chaos. The War of the Worlds is renowned for its groundbreaking special effects, which helped set a standard for future science fiction films, and its commentary on the anxieties of the Cold War era.", "https://upload.wikimedia.org/wikipedia/en/thumb/8/83/War_of_the_Worlds_2005_poster.jpg/220px-War_of_the_Worlds_2005_poster.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("Crime and Punishment", "A man wrestles with morality after committing a crime.", "Crime and Punishment is a 1935 film adaptation of Fyodor Dostoevsky's novel of the same name, directed by Josef von Sternberg. The story centers on Rodion Raskolnikov, a former student living in poverty in St. Petersburg who becomes obsessed with the idea of committing the perfect crime. He believes that certain extraordinary individuals have the right to transgress moral boundaries for a greater good. Raskolnikov murders a pawnbroker, but the act leads him into a spiral of guilt and paranoia. As he grapples with his conscience, he encounters various characters who challenge his beliefs and force him to confront the consequences of his actions. The film delves into themes of morality, redemption, and the psychological torment that follows sin.", "https://upload.wikimedia.org/wikipedia/en/b/b4/Crime_and_Punishment_%282002_British_film%29.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Brothers Karamazov", "A philosophical exploration of morality.", "The Brothers Karamazov is a 1958 film directed by Richard Brooks, adapted from Fyodor Dostoevsky's classic novel. It follows the complex relationships among the Karamazov brothers—Dmitri, Ivan, and Alexei—and their father, Fyodor Pavlovich. Each brother represents different philosophical ideals: Dmitri is passionate and reckless, Ivan is rational and skeptical, and Alexei is deeply spiritual.", "https://upload.wikimedia.org/wikipedia/en/thumb/4/4b/Brothers_Karamazov.jpeg/220px-Brothers_Karamazov.jpeg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Grapes of Wrath", "A family struggles during the Great Depression.", "The Grapes of Wrath is a 1940 film directed by John Ford, based on John Steinbeck's novel, depicting the Joad family's journey during the Great Depression as they are forced off their Oklahoma farm. Seeking a better life in California, they encounter hardships such as poverty and exploitation, highlighting themes of resilience, solidarity, and social injustice. The film poignantly portrays the struggles of migrant workers, serving as a powerful commentary on the American experience during a tumultuous era.", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/The_Grapes_of_Wrath_%281939_1st_ed_cover%29.jpg/220px-The_Grapes_of_Wrath_%281939_1st_ed_cover%29.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false)),
                new CreateMovieDTO("The Road", "A father and son survive in a post-apocalyptic world.", "The Road is a 2009 film directed by John Hillcoat, based on Cormac McCarthy's novel, set in a post-apocalyptic world where a father and his young son journey through a bleak landscape devastated by an unspecified cataclysm. Struggling to survive against the elements and desperate scavengers, they cling to their humanity and moral compass while searching for safety and sustenance. The film explores themes of love, survival, and the enduring bond between parent and child amidst overwhelming despair, creating a haunting reflection on hope in the face of darkness.", "https://upload.wikimedia.org/wikipedia/en/thumb/a/a7/The_Road_movie_poster.jpg/220px-The_Road_movie_poster.jpg",
                        getRandomPlatforms(true), getRandomPlatforms(false))
        );

        movies.forEach(movieService::createMovie);
    }

    private List<String> getRandomPlatforms(boolean forBuying) {
        int numberOfPlatforms = RANDOM.nextInt(ALL_PLATFORMS.size()) + 1;
        List<String> platforms = new ArrayList<>(ALL_PLATFORMS);
        Collections.shuffle(platforms);

        return platforms.stream().limit(numberOfPlatforms).toList();
    }
}