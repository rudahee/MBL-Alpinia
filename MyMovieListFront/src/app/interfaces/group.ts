import { JoinGroupComponent } from './../components/common/join-group/join-group.component';
export interface GroupInterface {
    name:String
    maxRating?:Number
    maxRatingUsername?:String
    minRating?:Number
    minRatingUsername?:String
    avgRating?:Number
}

export interface CreateGroupInterface {
    name: String;
    creatorId: String;
}

export interface GroupIdResponseInterface {
    id: String;
}

export interface SimpleGroupInterface {
    id: String
    name: string
}

export interface GroupWithMembersInterface {
    id: String
    name: string
    members: Array<String>
}

export interface GroupPageInterface {
    id: String
    name: String
    inviteCode: String 
    movies: Array<MoviePageInterface>         
}

export interface MoviePageInterface {
    id: String
    name: String
    imageUrl: String
    buyOn: Array<String>
    watchOn: Array<String>
    averageGroupRating: Number,
    users: Array<UserPageRatingInterface>
}

export interface UserPageRatingInterface {
    id: String
    name: String
    rating: Number
    comment: String
}