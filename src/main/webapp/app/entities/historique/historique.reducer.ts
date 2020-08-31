import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHistorique, defaultValue } from 'app/shared/model/historique.model';

export const ACTION_TYPES = {
  FETCH_HISTORIQUE_LIST: 'historique/FETCH_HISTORIQUE_LIST',
  FETCH_HISTORIQUE: 'historique/FETCH_HISTORIQUE',
  CREATE_HISTORIQUE: 'historique/CREATE_HISTORIQUE',
  UPDATE_HISTORIQUE: 'historique/UPDATE_HISTORIQUE',
  DELETE_HISTORIQUE: 'historique/DELETE_HISTORIQUE',
  RESET: 'historique/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHistorique>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type HistoriqueState = Readonly<typeof initialState>;

// Reducer

export default (state: HistoriqueState = initialState, action): HistoriqueState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HISTORIQUE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HISTORIQUE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_HISTORIQUE):
    case REQUEST(ACTION_TYPES.UPDATE_HISTORIQUE):
    case REQUEST(ACTION_TYPES.DELETE_HISTORIQUE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_HISTORIQUE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HISTORIQUE):
    case FAILURE(ACTION_TYPES.CREATE_HISTORIQUE):
    case FAILURE(ACTION_TYPES.UPDATE_HISTORIQUE):
    case FAILURE(ACTION_TYPES.DELETE_HISTORIQUE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_HISTORIQUE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_HISTORIQUE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_HISTORIQUE):
    case SUCCESS(ACTION_TYPES.UPDATE_HISTORIQUE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_HISTORIQUE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/historiques';

// Actions

export const getEntities: ICrudGetAllAction<IHistorique> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_HISTORIQUE_LIST,
    payload: axios.get<IHistorique>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IHistorique> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HISTORIQUE,
    payload: axios.get<IHistorique>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IHistorique> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HISTORIQUE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHistorique> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HISTORIQUE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHistorique> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HISTORIQUE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
