export interface LoginInterface {
    username: String;
    password: String;
}

export interface SuccessfullyLoggedInterface {
    token: string;
    expiration_time: number;
}

export interface DecodedTokenInterface {
    sub: string
    iat: number
    exp: number
    id: string
    roles: string
}