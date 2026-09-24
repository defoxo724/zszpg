import ListGroup from 'react-bootstrap/ListGroup'
import type { MealCategory } from '../../types'
import EntityActionButtons from '../common/EntityActionButtons'
import TruncatedText from '../common/TruncatedText'

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
					<TruncatedText text={category.name} maxWidth="420px" />
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
