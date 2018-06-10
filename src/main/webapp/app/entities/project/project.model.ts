import { BaseEntity } from './../../shared';

export const enum ProjectStatus {
    'ACTIVE',
    'INACTIVE',
    'CLOSED'
}

export class Project implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: any,
        public projectStatus?: ProjectStatus,
        public startDate?: any,
        public endDate?: any,
        public owner?: string,
        public tasks?: BaseEntity[],
        public documents?: BaseEntity[],
        public assignedToLogin?: string,
        public assignedToId?: number,
        public issueTickets?: BaseEntity[],
    ) {
    }
}
