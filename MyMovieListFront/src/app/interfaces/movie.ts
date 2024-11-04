export interface MovieRegisterInterface {
    title:String;
    imageUrl:String;
    synopsis:String;
    shortSynopsis:String;
    watchOn: Array<String>;
    buyOn: Array<String>;
}

export interface MovieInterface {
    name:String;
    imageUrl:String;
    synopsis:String;
    shortSynopsis:String;
    watchOn: Array<String>;
    buyOn: Array<String>;
    id: String;
    mediaRating?:Number;
    mediaGlobalRating?:Number;
}

export interface PageableMovieInterface {
    content: Array<MovieInterface>        
    totalPages: Number,
    last: Boolean,
    totalElements: Number,
    size: Number,
    number: Number,
    first: Boolean,
    numberOfElements: Number,
    empty: Boolean  
}


export interface UpdatedMovieRatingInterface {
    id: String
    mediaRating: Number
    yourRating?: Number
    comment: String
}

export interface MovieIdResponseInterface {
    id: String;
}

export interface RatingMovieInterface {
    movieId?: String;
    userId?: String;
    rating: Number;
    comment: String;
}