import { MatchingService } from './matching.service';
export declare class MatchingController {
    private readonly service;
    constructor(service: MatchingService);
    run(ofertaId: string): Promise<any>;
    getMatches(ofertaId: string, sortBy?: string, order?: string): Promise<{
        data: any[];
        total: number;
    }>;
}
