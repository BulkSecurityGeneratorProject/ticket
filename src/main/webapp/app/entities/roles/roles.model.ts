import { BaseEntity } from './../../shared';

export class Roles implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public assigsId?: number,
        public tasksId?: number,
    ) {
    }
}
