import { BaseEntity } from './../../shared';

export const enum TaskStatus {
    'STATUS_ACTIVE',
    'STATUS_DONE',
    'STATUS_FAILED',
    'STATUS_SUSPENDED',
    'STATUS_UNDEFINED'
}

export class Tasks implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public level?: number,
        public taskStatus?: TaskStatus,
        public start?: string,
        public end?: string,
        public duration?: number,
        public startIsMilestone?: boolean,
        public endIsMilestone?: boolean,
        public depends?: string,
        public description?: string,
        public progress?: number,
        public selectedRow?: number,
        public canWrite?: boolean,
        public canWriteOnParent?: boolean,
        public roles?: BaseEntity[],
        public resources?: BaseEntity[],
        public documents?: BaseEntity[],
        public assigsId?: number,
        public projectName?: string,
        public projectId?: number,
    ) {
        this.startIsMilestone = false;
        this.endIsMilestone = false;
        this.canWrite = false;
        this.canWriteOnParent = false;
    }
}
