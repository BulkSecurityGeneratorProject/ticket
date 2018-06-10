/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TicketTestModule } from '../../../test.module';
import { RolesDetailComponent } from '../../../../../../main/webapp/app/entities/roles/roles-detail.component';
import { RolesService } from '../../../../../../main/webapp/app/entities/roles/roles.service';
import { Roles } from '../../../../../../main/webapp/app/entities/roles/roles.model';

describe('Component Tests', () => {

    describe('Roles Management Detail Component', () => {
        let comp: RolesDetailComponent;
        let fixture: ComponentFixture<RolesDetailComponent>;
        let service: RolesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TicketTestModule],
                declarations: [RolesDetailComponent],
                providers: [
                    RolesService
                ]
            })
            .overrideTemplate(RolesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RolesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RolesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Roles(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.roles).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
