/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TicketTestModule } from '../../../test.module';
import { RaiseTicketComponent } from '../../../../../../main/webapp/app/entities/raise-ticket/raise-ticket.component';
import { RaiseTicketService } from '../../../../../../main/webapp/app/entities/raise-ticket/raise-ticket.service';
import { RaiseTicket } from '../../../../../../main/webapp/app/entities/raise-ticket/raise-ticket.model';

describe('Component Tests', () => {

    describe('RaiseTicket Management Component', () => {
        let comp: RaiseTicketComponent;
        let fixture: ComponentFixture<RaiseTicketComponent>;
        let service: RaiseTicketService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TicketTestModule],
                declarations: [RaiseTicketComponent],
                providers: [
                    RaiseTicketService
                ]
            })
            .overrideTemplate(RaiseTicketComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RaiseTicketComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RaiseTicketService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new RaiseTicket(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.raiseTickets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
