import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Resources } from './resources.model';
import { ResourcesPopupService } from './resources-popup.service';
import { ResourcesService } from './resources.service';

@Component({
    selector: 'jhi-resources-delete-dialog',
    templateUrl: './resources-delete-dialog.component.html'
})
export class ResourcesDeleteDialogComponent {

    resources: Resources;

    constructor(
        private resourcesService: ResourcesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.resourcesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'resourcesListModification',
                content: 'Deleted an resources'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-resources-delete-popup',
    template: ''
})
export class ResourcesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resourcesPopupService: ResourcesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.resourcesPopupService
                .open(ResourcesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
