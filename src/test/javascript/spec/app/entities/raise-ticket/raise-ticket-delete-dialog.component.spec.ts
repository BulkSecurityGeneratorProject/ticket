/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TicketTestModule } from '../../../test.module';
import { RaiseTicketDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/raise-ticket/raise-ticket-delete-dialog.component';
import { RaiseTicketService } from '../../../../../../main/webapp/app/entities/raise-ticket/raise-ticket.service';

describe('Component Tests', () => {

    describe('RaiseTicket Management Delete Component', () => {
        let comp: RaiseTicketDeleteDialogComponent;
        let fixture: ComponentFixture<RaiseTicketDeleteDialogComponent>;
        let service: RaiseTicketService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TicketTestModule],
                declarations: [RaiseTicketDeleteDialogComponent],
                providers: [
                    RaiseTicketService
                ]
            })
            .overrideTemplate(RaiseTicketDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RaiseTicketDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RaiseTicketService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
