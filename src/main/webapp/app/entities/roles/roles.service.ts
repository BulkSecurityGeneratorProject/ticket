import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Roles } from './roles.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Roles>;

@Injectable()
export class RolesService {

    private resourceUrl =  SERVER_API_URL + 'api/roles';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/roles';

    constructor(private http: HttpClient) { }

    create(roles: Roles): Observable<EntityResponseType> {
        const copy = this.convert(roles);
        return this.http.post<Roles>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(roles: Roles): Observable<EntityResponseType> {
        const copy = this.convert(roles);
        return this.http.put<Roles>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Roles>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Roles[]>> {
        const options = createRequestOption(req);
        return this.http.get<Roles[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Roles[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Roles[]>> {
        const options = createRequestOption(req);
        return this.http.get<Roles[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Roles[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Roles = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Roles[]>): HttpResponse<Roles[]> {
        const jsonResponse: Roles[] = res.body;
        const body: Roles[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Roles.
     */
    private convertItemFromServer(roles: Roles): Roles {
        const copy: Roles = Object.assign({}, roles);
        return copy;
    }

    /**
     * Convert a Roles to a JSON which can be sent to the server.
     */
    private convert(roles: Roles): Roles {
        const copy: Roles = Object.assign({}, roles);
        return copy;
    }
}
