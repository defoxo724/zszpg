import ListGroup from 'react-bootstrap/ListGroup'
import type { MealCategory } from '../../types'
import EntityActionButtons from '../common/EntityActionButtons'

interface MealCategoryListProps {
	categories: MealCategory[]
	isAdmin: boolean
	onEdit: (category: MealCategory) => void
	onDelete: (id: number) => void
}

const MealCategoryList = (props: MealCategoryListProps) => (
	<>
		<ListGroup>
			{props.categories.map((category) => (
				<ListGroup.Item
					className="d-flex justify-content-between align-items-center"
					key={category.id}
				>
					<span>{category.name}</span>
					{props.isAdmin && (
						<EntityActionButtons
							onEdit={() => props.onEdit(category)}
							onDelete={() => props.onDelete(category.id)}
						/>
					)}
				</ListGroup.Item>
			))}
		</ListGroup>
		{!props.categories.length && <p>Brak kategorii.</p>}
	</>
)

export default MealCategoryList
