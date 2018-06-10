import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RaiseTicket } from './raise-ticket.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<RaiseTicket>;

@Injectable()
export class RaiseTicketService {

    private resourceUrl =  SERVER_API_URL + 'api/raise-tickets';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/raise-tickets';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(raiseTicket: RaiseTicket): Observable<EntityResponseType> {
        const copy = this.convert(raiseTicket);
        return this.http.post<RaiseTicket>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(raiseTicket: RaiseTicket): Observable<EntityResponseType> {
        const copy = this.convert(raiseTicket);
        return this.http.put<RaiseTicket>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RaiseTicket>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RaiseTicket[]>> {
        const options = createRequestOption(req);
        return this.http.get<RaiseTicket[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RaiseTicket[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<RaiseTicket[]>> {
        const options = createRequestOption(req);
        return this.http.get<RaiseTicket[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RaiseTicket[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RaiseTicket = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RaiseTicket[]>): HttpResponse<RaiseTicket[]> {
        const jsonResponse: RaiseTicket[] = res.body;
        const body: RaiseTicket[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RaiseTicket.
     */
    private convertItemFromServer(raiseTicket: RaiseTicket): RaiseTicket {
        const copy: RaiseTicket = Object.assign({}, raiseTicket);
        copy.openDate = this.dateUtils
            .convertLocalDateFromServer(raiseTicket.openDate);
        copy.dueDate = this.dateUtils
            .convertLocalDateFromServer(raiseTicket.dueDate);
        return copy;
    }

    /**
     * Convert a RaiseTicket to a JSON which can be sent to the server.
     */
    private convert(raiseTicket: RaiseTicket): RaiseTicket {
        const copy: RaiseTicket = Object.assign({}, raiseTicket);
        copy.openDate = this.dateUtils
            .convertLocalDateToServer(raiseTicket.openDate);
        copy.dueDate = this.dateUtils
            .convertLocalDateToServer(raiseTicket.dueDate);
        return copy;
    }
}
