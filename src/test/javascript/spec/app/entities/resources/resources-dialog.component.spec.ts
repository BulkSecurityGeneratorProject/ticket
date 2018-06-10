/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TicketTestModule } from '../../../test.module';
import { ResourcesDialogComponent } from '../../../../../../main/webapp/app/entities/resources/resources-dialog.component';
import { ResourcesService } from '../../../../../../main/webapp/app/entities/resources/resources.service';
import { Resources } from '../../../../../../main/webapp/app/entities/resources/resources.model';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { AssigsService } from '../../../../../../main/webapp/app/entities/assigs';
import { TasksService } from '../../../../../../main/webapp/app/entities/tasks';

describe('Component Tests', () => {

    describe('Resources Management Dialog Component', () => {
        let comp: ResourcesDialogComponent;
        let fixture: ComponentFixture<ResourcesDialogComponent>;
        let service: ResourcesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TicketTestModule],
                declarations: [ResourcesDialogComponent],
                providers: [
                    UserService,
                    AssigsService,
                    TasksService,
                    ResourcesService
                ]
            })
            .overrideTemplate(ResourcesDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ResourcesDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResourcesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Resources(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.resources = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'resourcesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Resources();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.resources = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'resourcesListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
