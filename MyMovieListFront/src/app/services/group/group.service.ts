import { CreateGroupInterface, GroupIdResponseInterface, GroupPageInterface, GroupWithMembersInterface } from './../../interfaces/group';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
import { JwtHandlerService } from '../jwt/JwtHandler.service';
import { UserGroupsInterface } from '../../interfaces/user_groups';



@Injectable({
  providedIn: 'root'
})
export class GroupService {

  constructor(private httpC: HttpClient, private jwtService: JwtHandlerService) { }

  createGroup(group: CreateGroupInterface): Observable<GroupIdResponseInterface> {
    return this.httpC.post<GroupIdResponseInterface>('/group', group);
  }

  joinGroup(inviteCode: String): Observable<GroupIdResponseInterface> {
    return this.httpC.put<GroupIdResponseInterface>('/group/'+ inviteCode, null);
  }

  getGroupWithMembers(id: String): Observable<Array<UserGroupsInterface>> {
    return this.httpC.get<Array<UserGroupsInterface>>('/group/members?id=' + id)
  }

  getGroupPageInfo(id: String): Observable<GroupPageInterface> {
    return this.httpC.get<GroupPageInterface>('/group/' + id)
  }

  getGroupMembers(id: string): Observable<GroupWithMembersInterface> {
    return this.httpC.get<GroupWithMembersInterface>(`/group/${id}/members`)
  }
}
