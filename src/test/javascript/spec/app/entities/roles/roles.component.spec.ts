/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TicketTestModule } from '../../../test.module';
import { RolesComponent } from '../../../../../../main/webapp/app/entities/roles/roles.component';
import { RolesService } from '../../../../../../main/webapp/app/entities/roles/roles.service';
import { Roles } from '../../../../../../main/webapp/app/entities/roles/roles.model';

describe('Component Tests', () => {

    describe('Roles Management Component', () => {
        let comp: RolesComponent;
        let fixture: ComponentFixture<RolesComponent>;
        let service: RolesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TicketTestModule],
                declarations: [RolesComponent],
                providers: [
                    RolesService
                ]
            })
            .overrideTemplate(RolesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RolesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RolesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Roles(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.roles[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
