import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Ticket } from './ticket.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Ticket>;

@Injectable()
export class TicketService {

    private resourceUrl =  SERVER_API_URL + 'api/tickets';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/tickets';

    constructor(private http: HttpClient) { }

    create(ticket: Ticket): Observable<EntityResponseType> {
        const copy = this.convert(ticket);
        return this.http.post<Ticket>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(ticket: Ticket): Observable<EntityResponseType> {
        const copy = this.convert(ticket);
        return this.http.put<Ticket>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Ticket>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Ticket[]>> {
        const options = createRequestOption(req);
        return this.http.get<Ticket[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Ticket[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Ticket[]>> {
        const options = createRequestOption(req);
        return this.http.get<Ticket[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Ticket[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Ticket = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Ticket[]>): HttpResponse<Ticket[]> {
        const jsonResponse: Ticket[] = res.body;
        const body: Ticket[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Ticket.
     */
    private convertItemFromServer(ticket: Ticket): Ticket {
        const copy: Ticket = Object.assign({}, ticket);
        return copy;
    }

    /**
     * Convert a Ticket to a JSON which can be sent to the server.
     */
    private convert(ticket: Ticket): Ticket {
        const copy: Ticket = Object.assign({}, ticket);
        return copy;
    }
}
