export interface UserGroupsInterface {
    name:String;
    users?:Array<String>
}

export interface GroupsForUserInterface {
    name: String
    groups: Array<GroupsInterface>
}

export interface GroupsInterface {
    name: String;
    id: String;
}