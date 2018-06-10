import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Resources } from './resources.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Resources>;

@Injectable()
export class ResourcesService {

    private resourceUrl =  SERVER_API_URL + 'api/resources';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/resources';

    constructor(private http: HttpClient) { }

    create(resources: Resources): Observable<EntityResponseType> {
        const copy = this.convert(resources);
        return this.http.post<Resources>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(resources: Resources): Observable<EntityResponseType> {
        const copy = this.convert(resources);
        return this.http.put<Resources>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Resources>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Resources[]>> {
        const options = createRequestOption(req);
        return this.http.get<Resources[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Resources[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Resources[]>> {
        const options = createRequestOption(req);
        return this.http.get<Resources[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Resources[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Resources = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Resources[]>): HttpResponse<Resources[]> {
        const jsonResponse: Resources[] = res.body;
        const body: Resources[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Resources.
     */
    private convertItemFromServer(resources: Resources): Resources {
        const copy: Resources = Object.assign({}, resources);
        return copy;
    }

    /**
     * Convert a Resources to a JSON which can be sent to the server.
     */
    private convert(resources: Resources): Resources {
        const copy: Resources = Object.assign({}, resources);
        return copy;
    }
}
