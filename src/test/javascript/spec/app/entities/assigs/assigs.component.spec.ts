/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TicketTestModule } from '../../../test.module';
import { AssigsComponent } from '../../../../../../main/webapp/app/entities/assigs/assigs.component';
import { AssigsService } from '../../../../../../main/webapp/app/entities/assigs/assigs.service';
import { Assigs } from '../../../../../../main/webapp/app/entities/assigs/assigs.model';

describe('Component Tests', () => {

    describe('Assigs Management Component', () => {
        let comp: AssigsComponent;
        let fixture: ComponentFixture<AssigsComponent>;
        let service: AssigsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TicketTestModule],
                declarations: [AssigsComponent],
                providers: [
                    AssigsService
                ]
            })
            .overrideTemplate(AssigsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssigsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssigsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Assigs(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.assigs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
