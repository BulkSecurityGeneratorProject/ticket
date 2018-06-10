import { BaseEntity } from './../../shared';

export class Assigs implements BaseEntity {
    constructor(
        public id?: number,
        public resourceId?: number,
        public roleId?: number,
        public taskId?: number,
        public effort?: number,
        public tasks?: BaseEntity[],
        public resources?: BaseEntity[],
        public roles?: BaseEntity[],
    ) {
    }
}
