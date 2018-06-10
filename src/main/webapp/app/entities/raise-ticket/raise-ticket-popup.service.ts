import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { RaiseTicket } from './raise-ticket.model';
import { RaiseTicketService } from './raise-ticket.service';

@Injectable()
export class RaiseTicketPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private raiseTicketService: RaiseTicketService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.raiseTicketService.find(id)
                    .subscribe((raiseTicketResponse: HttpResponse<RaiseTicket>) => {
                        const raiseTicket: RaiseTicket = raiseTicketResponse.body;
                        if (raiseTicket.openDate) {
                            raiseTicket.openDate = {
                                year: raiseTicket.openDate.getFullYear(),
                                month: raiseTicket.openDate.getMonth() + 1,
                                day: raiseTicket.openDate.getDate()
                            };
                        }
                        if (raiseTicket.dueDate) {
                            raiseTicket.dueDate = {
                                year: raiseTicket.dueDate.getFullYear(),
                                month: raiseTicket.dueDate.getMonth() + 1,
                                day: raiseTicket.dueDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.raiseTicketModalRef(component, raiseTicket);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.raiseTicketModalRef(component, new RaiseTicket());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    raiseTicketModalRef(component: Component, raiseTicket: RaiseTicket): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.raiseTicket = raiseTicket;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
