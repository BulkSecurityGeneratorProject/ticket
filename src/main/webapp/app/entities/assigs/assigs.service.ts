import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Assigs } from './assigs.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Assigs>;

@Injectable()
export class AssigsService {

    private resourceUrl =  SERVER_API_URL + 'api/assigs';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/assigs';

    constructor(private http: HttpClient) { }

    create(assigs: Assigs): Observable<EntityResponseType> {
        const copy = this.convert(assigs);
        return this.http.post<Assigs>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(assigs: Assigs): Observable<EntityResponseType> {
        const copy = this.convert(assigs);
        return this.http.put<Assigs>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Assigs>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Assigs[]>> {
        const options = createRequestOption(req);
        return this.http.get<Assigs[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Assigs[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Assigs[]>> {
        const options = createRequestOption(req);
        return this.http.get<Assigs[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Assigs[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Assigs = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Assigs[]>): HttpResponse<Assigs[]> {
        const jsonResponse: Assigs[] = res.body;
        const body: Assigs[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Assigs.
     */
    private convertItemFromServer(assigs: Assigs): Assigs {
        const copy: Assigs = Object.assign({}, assigs);
        return copy;
    }

    /**
     * Convert a Assigs to a JSON which can be sent to the server.
     */
    private convert(assigs: Assigs): Assigs {
        const copy: Assigs = Object.assign({}, assigs);
        return copy;
    }
}
