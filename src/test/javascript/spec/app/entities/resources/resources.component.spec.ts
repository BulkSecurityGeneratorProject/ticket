/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TicketTestModule } from '../../../test.module';
import { ResourcesComponent } from '../../../../../../main/webapp/app/entities/resources/resources.component';
import { ResourcesService } from '../../../../../../main/webapp/app/entities/resources/resources.service';
import { Resources } from '../../../../../../main/webapp/app/entities/resources/resources.model';

describe('Component Tests', () => {

    describe('Resources Management Component', () => {
        let comp: ResourcesComponent;
        let fixture: ComponentFixture<ResourcesComponent>;
        let service: ResourcesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TicketTestModule],
                declarations: [ResourcesComponent],
                providers: [
                    ResourcesService
                ]
            })
            .overrideTemplate(ResourcesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ResourcesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResourcesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Resources(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.resources[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
