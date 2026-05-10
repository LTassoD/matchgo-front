export declare class MatchingService {
    runMatching(ofertaId: string): Promise<any>;
    getMatches(ofertaId: string, sortBy?: string, order?: string): Promise<{
        data: any[];
        total: number;
    }>;
}
